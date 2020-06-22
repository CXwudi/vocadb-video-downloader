package mikufan.cx.vocadb_pv_task_producer.main;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.task.VocaDbPvTask;
import mikufan.cx.common_vocaloid_entity.vocadb.ResponseSongList;
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

  public VocaDbTaskUpdater(int listId, @NonNull String userAgent, int maxResult) {
    this.listId = listId;
    this.userAgent = userAgent;
    fetcher = new ListFetcher(maxResult);
  }

  /**
   * create or update an existing {@link VocaDbPvTask} and the reference {@link ResponseSongList}
   *
   * @param inputTask the old {@link VocaDbPvTask}
   * @param inputRef the old {@link ResponseSongList}
   * @return new updated task and the reference
   */
  public Pair<VocaDbPvTask, ResponseSongList> createOrUpdate(
      VocaDbPvTask inputTask, ResponseSongList inputRef, String taskNewName) {
    var task = Objects.requireNonNullElse(inputTask, new VocaDbPvTask(taskNewName));
    //TODO: link the list fetcher and we done
    return Tuples.pair(task, inputRef);
  }
}
