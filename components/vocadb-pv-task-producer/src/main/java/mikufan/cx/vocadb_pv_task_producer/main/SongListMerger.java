package mikufan.cx.vocadb_pv_task_producer.main;

import mikufan.cx.common_vocaloid_entity.pv.PvService;
import mikufan.cx.common_vocaloid_entity.task.VocaDbPvTask;
import mikufan.cx.common_vocaloid_entity.vocadb.api.songList.get_listid_songs.PartialSongList;
import org.eclipse.collections.api.list.ImmutableList;

public class SongListMerger {

  public PartialSongList mergeToList(PartialSongList oldList, PartialSongList newList) {
    return null;
  }

  public VocaDbPvTask mergeToTask(
      VocaDbPvTask oldTask,
      PartialSongList newList,
      String newName,
      ImmutableList<PvService> pvPref) {
    return null;
  }
}
