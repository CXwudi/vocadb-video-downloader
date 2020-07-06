package mikufan.cx.vocadb_pv_task_producer.main;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.pv.VocaDbPv;
import mikufan.cx.common_vocaloid_entity.task.VocaDbPvTask;
import mikufan.cx.common_vocaloid_entity.vocadb.api.songList.get_listid_songs.PartialSongList;
import mikufan.cx.common_vocaloid_entity.vocadb.api.songList.get_listid_songs.SongInListForApiContract;
import mikufan.cx.common_vocaloid_entity.vocadb.models.PVContract;
import mikufan.cx.project_vd_common_util.pv_service.SupportedPvServices;
import org.eclipse.collections.api.factory.Lists;
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
   * merge two {@link PartialSongList} into one, by appending the second one into the first one
   */
  public PartialSongList mergeToList(PartialSongList oldList, @NonNull PartialSongList newList) {
    if (oldList == null){
      log.info("since no old reference exists, returning new song list as the reference");
      return newList;
    } else {
      log.info("merging new song list into the reference");
    }

    //get all songs that are not present in oldList
    var oldIds = oldList.getItems().asLazy().collectInt(item -> item.getSong().getId()).toSet();
    var newAddedItems = newList.getItems().asLazy().rejectWith(
        (item, ids) -> ids.contains(item.getSong().getId())
        , oldIds).toList();

    //construct the new list with new items appended and correct the index
    var oldItems = Lists.mutable.withAll(oldList.getItems());
    oldItems.addAll(newAddedItems);
    var newItems = oldItems.asLazy()
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
   * update or construct the new task
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
      @NonNull ImmutableList<String> pvPref) {
    VocaDbPvTask newTask = Objects.requireNonNullElse(oldTask, new VocaDbPvTask(newName));
    log.info("updating the task {} with new response song list", newTask.getFolderName());
    // get a list of new songs, no matter if PV is still available or not
    var pvIdSet = newTask.getDone().collectInt(VocaDbPv::getSongId).toSet();
    pvIdSet.addAll(newTask.getTodo().collectInt(VocaDbPv::getSongId));
    var newAddedSongs = newList.getItems().asLazy()
        .collect(SongInListForApiContract::getSong)
        .rejectWith((song, set) -> set.contains(song.getId()), pvIdSet)
        .toList();

    // with a list of new songs, according to status, map them to new item in to-do or failed
    for (var song : newAddedSongs){
      var pvs = song.getPvs();
      var name = song.getName();
      //check if no pvs
      if (pvs.isEmpty()){
        log.warn("no PVs found in {}", name);
        newTask.markError(name, "no PVs");
        continue;
      }

      // sort by pv pref
      var prefWithIdx = pvPref.asLazy().zipWithIndex().toMap(Pair::getOne, Pair::getTwo);
      var sortedPvs = pvs.sortThisByInt(pv -> prefWithIdx.get(pv.getService()));

      //filter out inaccessible pvs
      var accessiblePvs = sortedPvs.reject(PVContract::isDisabled);
      if (accessiblePvs.isEmpty()){
        log.warn("all PVs for {} are not accessible on website", name);
        var firstPv = pvs.get(0);
        newTask.markError(
            new VocaDbPv(firstPv.getPvId(), firstPv.getService(), firstPv.getName(), song.getId()),
            "deleted PV");
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

      // according to pv pref add the task
      var selectedPv = supportedPvs.get(0);
      var vocaDbPv = new VocaDbPv(selectedPv.getPvId(), selectedPv.getService(), selectedPv.getName(), song.getId());
      log.debug("adding new PV {} to task: {}", vocaDbPv, newTask.getFolderName());
      newTask.markTodo(vocaDbPv);
    }

    return newTask;
  }
}
