package mikufan.cx.vocadb_pv_task_producer.main;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mikufan.cx.vocadb_pv_task_producer.config.entity.AppConfig;

@RequiredArgsConstructor
public class VocaDbTaskManager {

  @NonNull private final AppConfig config;

  public void doWork() throws Exception {
    //1. read local task if exist

    //2. read local ref if exist

    //3. update or create the task and the ref
    var producer = new VocaDbTaskUpdater(
        config.userConfig.getListId(),
        config.userConfig.getUserAgent(),
        config.systemConfig.getMaxResult());

    //4. write back task

    //5. write back ref
  }
}
