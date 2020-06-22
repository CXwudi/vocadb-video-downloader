package mikufan.cx.vocadb_pv_task_producer.main;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.pv.PvService;
import mikufan.cx.common_vocaloid_entity.task.VocaDbPvTask;
import mikufan.cx.common_vocaloid_entity.vocadb.ResponseSongList;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.tuple.Tuples;

import java.util.Objects;

/**
 * A stateless task producer to update or create the VocaDb pv task and the reference json
 * @author CX无敌
 */
@Slf4j
public class VocaDbTaskUpdater {

  private final int listId;
  private final ListFetcher fetcher;
  private final String userAgent;
  private final ImmutableList<PvService> pvPref;

  public VocaDbTaskUpdater(int listId, @NonNull String userAgent, int maxResult, ImmutableList<PvService> pvPref) {
    this.listId = listId;
    this.userAgent = userAgent;
    fetcher = new ListFetcher(maxResult);
    this.pvPref = pvPref;
  }

  /**
   * create or update an existing {@link VocaDbPvTask} and the reference {@link ResponseSongList}
   *
   * @param inputTask the old {@link VocaDbPvTask}, nullable
   * @param inputRef the old {@link ResponseSongList}, nullable
   * @return new updated or created task and the reference
   */
  public Pair<VocaDbPvTask, ResponseSongList> createOrUpdate(
      VocaDbPvTask inputTask, ResponseSongList inputRef, String taskNewName) throws VocaDbPvTaskException {
    var task = Objects.requireNonNullElse(inputTask, new VocaDbPvTask(taskNewName));
    var songLists = fetcher.getVocadbListOfId(listId, userAgent);
    //TODO: we decide to use another separate class for handling "various artist"
    //TODO: link the list fetcher and we done
    addToTask(task, songLists);
    mergeRef(inputRef, songLists);
    return Tuples.pair(task, inputRef);
  }

  /**
   *
   * @param task
   * @param songLists
   */
  protected void addToTask(VocaDbPvTask task, ResponseSongList songLists) {

  }

  protected void mergeRef(ResponseSongList inputRef, ResponseSongList songLists) {

  }
}
