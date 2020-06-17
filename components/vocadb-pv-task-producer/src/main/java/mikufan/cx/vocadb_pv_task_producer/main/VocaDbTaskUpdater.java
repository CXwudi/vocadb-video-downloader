package mikufan.cx.vocadb_pv_task_producer.main;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_entity.task.VocaDbPvTask;
import mikufan.cx.common_entity.vocadb.ResponseSongList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.tuple.Tuples;

/**
 * A stateless task producer to update or create the VocaDb pv task and the reference json
 * @author CX无敌
 */
@Slf4j
public class VocaDbTaskUpdater {

  private final int listId;
  private final String userAgent;
  private final ListFetcher fetcher;

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
  public Pair<VocaDbPvTask, ResponseSongList> createOrUpdate(VocaDbPvTask inputTask, ResponseSongList inputRef) {

    return Tuples.pair(inputTask, inputRef);
  }
}
