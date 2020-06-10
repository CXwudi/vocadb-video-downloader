package mikufan.cx.vocadb_pv_task_producer;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_task_producer.config.parser.ConfigFactory;

/**
 * Main class
 * @author CX无敌
 */
@Slf4j
public class Main {
  public static void main(String[] args) {
    var appConfig = ConfigFactory.parse(args);
    var id = appConfig.userConfig.getListId();
    log.info("id = {}", id);
  }
}
