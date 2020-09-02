package mikufan.cx.vocadb_pv_task_producer;

import mikufan.cx.vocadb_pv_task_producer.controllers.MainRunner;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * main entry of this module
 * @author CX无敌
 */
@SpringBootApplication
public class TaskProducerApplication {
  public static void main(String[] args) throws VocaDbPvTaskException {
    var context = SpringApplication.run(TaskProducerApplication.class, args);
    //running our own class
    context.getBean(MainRunner.class).run(args);
  }
}
