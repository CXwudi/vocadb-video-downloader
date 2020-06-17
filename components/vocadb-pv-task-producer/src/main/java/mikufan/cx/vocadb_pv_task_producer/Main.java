package mikufan.cx.vocadb_pv_task_producer;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_entity.task.VocaDbPvTask;
import mikufan.cx.common_entity.vocadb.ResponseSongList;
import mikufan.cx.common_util.io.JacksonPojoTranslator;
import mikufan.cx.vocadb_pv_task_producer.config.parser.ConfigFactory;
import mikufan.cx.vocadb_pv_task_producer.main.VocaDbTaskUpdater;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;

import java.nio.file.Files;

/**
 * Meta main class, it shows all procedures
 * @author CX无敌
 */
@Slf4j
public class Main {
  public static void main(String[] args) throws VocaDbPvTaskException {
    /* ==  0. reading cmd == */
    var appConfig = ConfigFactory.parse(args);

    /* == 1. read local task if exist == */
    var taskFile = appConfig.userConfig.getTaskJsonFile();
    var taskFileReader = JacksonPojoTranslator.<VocaDbPvTask>createWithDefaultMapper();
    // no need to check if it is directory, app config should handled it
    var task = Files.exists(taskFile)
        ? taskFileReader.read(taskFile)
        .orElseThrow(() -> new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_009, "Invalid task json file"))
        : null;

    /* ==  2. read local ref if exist == */
    var refFile = appConfig.userConfig.getReferenceJsonFile();
    var refFileReader = JacksonPojoTranslator.<ResponseSongList>createWithDefaultMapper();
    var ref = Files.exists(refFile)
        ? refFileReader.read(refFile)
        .orElseThrow(() -> new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_010, "Invalid ref json file"))
        : null;

    /* ==  3. update or create the task and the ref  == */
    var producer = new VocaDbTaskUpdater(
        appConfig.userConfig.getListId(),
        appConfig.userConfig.getUserAgent(),
        appConfig.systemConfig.getMaxResult());

    var updatedTaskRefPair = producer.createOrUpdate(task, ref);

    /* ==  4. write back task == */
    taskFileReader.write(updatedTaskRefPair.getOne(), taskFile);
    /* ==  5. write back ref == */
    refFileReader.write(updatedTaskRefPair.getTwo(), refFile);

  }
}
