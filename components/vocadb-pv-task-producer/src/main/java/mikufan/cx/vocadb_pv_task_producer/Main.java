package mikufan.cx.vocadb_pv_task_producer;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_task_producer.config.parser.ConfigFactory;
import mikufan.cx.vocadb_pv_task_producer.main.VocaDbTaskUpdater;

/**
 * Meta main class, it shows all procedures
 * @author CX无敌
 */
@Slf4j
public class Main {
  public static void main(String[] args) {
    var appConfig = ConfigFactory.parse(args);
    var id = appConfig.userConfig.getListId();
    log.info("id = {}", id);

    // == 1. read local task if exist ==

    // == 2. read local ref if exist ==

    // == 3. update or create the task and the ref  ==
    var producer = new VocaDbTaskUpdater(
        appConfig.userConfig.getListId(),
        appConfig.userConfig.getUserAgent(),
        appConfig.systemConfig.getMaxResult());

    // == 4. write back task ==

    // == 5. write back ref ==

  }
}
