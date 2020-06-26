package mikufan.cx.vocadb_pv_task_producer.main;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.pv.PvService;
import mikufan.cx.common_vocaloid_entity.task.VocaDbPvTask;
import mikufan.cx.common_vocaloid_entity.vocadb.api.songList.get_listid_songs.PartialSongList;
import mikufan.cx.common_vocaloid_entity.vocadb.api.songList.get_listid_songs.SongInListForApiContract;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Objects;

/**
 * @author CX无敌
 */
@Slf4j
public class SongListMerger {

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
    var ids = oldList.getItems().asLazy().collectInt(item -> item.getSong().getId()).toSet();
    var newAddedItems = newList.getItems().asLazy().rejectWith(
        (item, oldIds) -> oldIds.contains(item.getSong().getId())
        , ids).toList();

    //construct the new list with new items appended and correct the index
    var oldItems = Lists.mutable.withAll(oldList.getItems());
    oldItems.addAll(newAddedItems);
    var newItems = oldItems.asLazy().zipWithIndex().collect((itemWithIdx) -> {
      var item = itemWithIdx.getOne();
      return SongInListForApiContract.builder()
          .notes(item.getNotes()).song(item.getSong())
          .order(itemWithIdx.getTwo() + 1)
          .build();
    }).toList();

    //save the new list back to old list
    oldList.getItems().clear();
    oldList.getItems().addAll(newItems);
    return oldList;
  }

  /**
   * update or construct the new task
   * @param oldTask the task to be updated.
   *                if null, new task is constructed with the given new name
   * @param newList
   * @param newName
   * @param pvPref
   * @return
   */
  public VocaDbPvTask mergeToTask(
      VocaDbPvTask oldTask,
      @NonNull PartialSongList newList,
      String newName,
      @NonNull ImmutableList<PvService> pvPref) {
    VocaDbPvTask newTask = null; Objects.requireNonNullElse(oldTask, new VocaDbPvTask(newName));

    return newTask;
  }
}
