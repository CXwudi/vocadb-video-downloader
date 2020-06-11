package mikufan.cx.vocadb_pv_task_producer.main;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_entity.task.VocaDbPvTask;
import mikufan.cx.common_entity.vocadb.ResponseSongList;
import mikufan.cx.common_role.task_producer.UpdatableTaskProducer;
import org.eclipse.collections.api.tuple.Pair;

/**
 * A stateless task producer to update or create the VocaDb pv task and the reference json
 * @author CX无敌
 */
@Slf4j
public class VocaDbTaskUpdater implements UpdatableTaskProducer<Pair<VocaDbPvTask, ResponseSongList>> {

  private final int listId;
  private final String userAgent;
  private final ListFetcher fetcher;

  public VocaDbTaskUpdater(int listId, @NonNull String userAgent, int maxResult) {
    this.listId = listId;
    this.userAgent = userAgent;
    fetcher = new ListFetcher(maxResult);
  }

  /**
   * create a new {@link VocaDbPvTask} and the reference {@link ResponseSongList}
   *
   * @return new task and the reference
   */
  @Override
  public Pair<VocaDbPvTask, ResponseSongList> create() {
    return null;
  }

  /**
   * update an existing {@link VocaDbPvTask} and the reference {@link ResponseSongList}
   *
   * @param taskWithRef the old {@link VocaDbPvTask} and the reference {@link ResponseSongList}
   * @return new updated task and the reference
   */
  @Override
  public Pair<VocaDbPvTask, ResponseSongList> update(Pair<VocaDbPvTask, ResponseSongList> taskWithRef) {
    return null;
  }
}
