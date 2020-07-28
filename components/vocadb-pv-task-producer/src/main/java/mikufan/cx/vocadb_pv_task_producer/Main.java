package mikufan.cx.vocadb_pv_task_producer;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_task_producer.config.parser.ConfigFactory;

@Slf4j
public class Main {

  public static void main(String[] args) {
    /* ==  0. reading cmd == */
    log.info("configuring application");
    var appConfig = ConfigFactory.parse(args);


  }
}
