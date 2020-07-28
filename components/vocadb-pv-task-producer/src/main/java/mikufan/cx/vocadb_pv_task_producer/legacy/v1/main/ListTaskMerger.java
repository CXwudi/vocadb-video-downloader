package mikufan.cx.vocadb_pv_task_producer.legacy.v1.main;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.pv.VocaDbPv;
import mikufan.cx.common_vocaloid_entity.task.VocaDbPvTask;
import mikufan.cx.common_vocaloid_entity.vocadb.api.songList.get_listid_songs.PartialSongList;
import mikufan.cx.common_vocaloid_entity.vocadb.api.songList.get_listid_songs.SongInListForApiContract;
import mikufan.cx.common_vocaloid_entity.vocadb.models.PVContract;
import mikufan.cx.project_vd_common_util.pv_service.SupportedPvServices;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.tuple.Pair;

import java.util.Objects;

/**
 * update or create the reference and task base on new gotten response list
 * @author CX无敌
 */
@Slf4j
public class ListTaskMerger {

  /**
   * merge two {@link PartialSongList} into one,
   * by updating the existing songs in first list, then appending the second one into the first one
   */
  public PartialSongList mergeToList(PartialSongList oldList, @NonNull PartialSongList newList) {
    if (oldList == null){
      log.info("since no old reference exists, returning new song list as the reference");
      return newList;
    } else {
      log.info("merging new song list into the reference");
    }

    //get all songs in old list that are not present in new list
    var newIds = newList.getItems().collectInt(item -> item.getSong().getId()).toSet();
    var oldItemsToPreserve = oldList.getItems().asLazy()
        .rejectWith((item, ids) -> ids.contains(item.getSong().getId()), newIds)
        .toList();

    // append new list to the end of old list
    oldItemsToPreserve.addAll(newList.getItems());

    //and re-index the list
    var newItems = oldItemsToPreserve.asLazy()
        .zipWithIndex()
        .collect(itemWithIdx -> {
          var item = itemWithIdx.getOne();
          return SongInListForApiContract.builder()
              .notes(item.getNotes()).song(item.getSong())
              .order(itemWithIdx.getTwo() + 1)
              .build();
        })
        .toList();

    //save the new list back to old list
    oldList.getItems().clear();
    oldList.getItems().addAll(newItems);
    oldList.setTotalCount(newItems.size());
    return oldList;
  }

  /**
   * update or construct the new task, and also update existing pvs in tasks if presented
   * @param oldTask the task to be updated.
   *                if null, new task is constructed with the given new name
   * @param newList the new gotten response song list from the http client
   * @param newName the name of the newly created task, not used if oldTask is not null
   * @param pvPref the preference of pv services
   * @return an updated task or a new task
   */
  public VocaDbPvTask mergeToTask(
      VocaDbPvTask oldTask,
      @NonNull PartialSongList newList,
      String newName,
      @NonNull ImmutableList<String> pvPref) throws VocaDbPvTaskException {
    if (oldTask == null && newName == null){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_307, "Both old task and new task name are null");
    }
    VocaDbPvTask newTask = Objects.requireNonNullElse(oldTask, new VocaDbPvTask(newName));
    log.info("updating the task {} with new response song list", newTask.getFolderName());
    // get a set of ids of songs in done set
    var doneIds = newTask.getDone().collectInt(VocaDbPv::getSongId).toSet();

    var newSongs = newList.getItems().asLazy()
        .collect(SongInListForApiContract::getSong)
        .toList();

    // with a list of songs from new list,
    // according to status, update the to-do, done or failed set
    for (var song : newSongs){
      // get the list of PVs of a song
      var pvs = song.getPvs();
      var name = song.getName();
      //check if no pvs
      if (pvs.isEmpty()){
        log.warn("no PVs found in {}", name);
        newTask.markError(name, "no official PVs");
        continue;
      }

      // sort by pv pref, un-supported pvs go last
      // first, get a map of pref service with int to indicate each service's priority
      var prefWithIdx = pvPref.asLazy().zipWithIndex().toMap(Pair::getOne, Pair::getTwo);
      // then, sort the pvs base on service priority numbers.
      // if a pv uses service that are not supported, put them at the back of the map
      var sortedPvs = pvs.sortThisByInt(pv -> {
        var service = pv.getService();
        return prefWithIdx.containsKey(service) ? prefWithIdx.get(service) : Integer.MAX_VALUE;
      });

      //filter out inaccessible pvs
      var accessiblePvs = sortedPvs.reject(PVContract::isDisabled);
      if (accessiblePvs.isEmpty()){
        log.warn("all PVs for {} are not accessible on website", name);
        var firstPv = pvs.get(0);
        newTask.markError(
            new VocaDbPv(firstPv.getPvId(), firstPv.getService(), firstPv.getName(), song.getId()),
            "officially deleted PV");
        continue;
      }

      //filter out not supported pvs
      var supportedPvs = accessiblePvs.reject(pv -> !SupportedPvServices.contains(pv.getService()));
      if (supportedPvs.isEmpty()){
        log.warn("current {} doesn't contains PVs that are accessible and supported by vocadb-pv-video-downloader", name);
        var firstPv = accessiblePvs.get(0);
        newTask.markError(
            new VocaDbPv(firstPv.getPvId(), firstPv.getService(), firstPv.getName(), song.getId()),
            "currently we are not able to download PV from " + firstPv.getService());
        continue;
      }

      // use the best preference pv to make the task
      var selectedPv = supportedPvs.get(0);
      var vocaDbPv = new VocaDbPv(selectedPv.getPvId(), selectedPv.getService(), selectedPv.getName(), song.getId());
      log.debug("adding new PV {} to task: {}", vocaDbPv, newTask.getFolderName());

      // add to proper set, only add to done set if done contains it
      if (doneIds.contains(song.getId())){
        if (newTask.getDone().contains(vocaDbPv)){
          newTask.getDone().remove(vocaDbPv);
        }
        newTask.markDone(vocaDbPv);
      } else { //to-do contains it or error contains it, or neither
        if (newTask.getTodo().contains(vocaDbPv)){
          newTask.getTodo().remove(vocaDbPv);
        }
        newTask.markTodo(vocaDbPv);
      }
    }

    return newTask;
  }
}
