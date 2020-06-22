package mikufan.cx.vocadb_pv_task_producer;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.task.VocaDbPvTask;
import mikufan.cx.common_vocaloid_entity.vocadb.ResponseSongList;
import mikufan.cx.common_vocaloid_util.io.JacksonPojoTransformer;
import mikufan.cx.vocadb_pv_task_producer.config.parser.ConfigFactory;
import mikufan.cx.vocadb_pv_task_producer.main.VocaDbTaskUpdater;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

/**
 * Meta main class, it shows all procedures
 * @author CX无敌
 */
@Slf4j
public class Main {
  public static void main(String[] args) throws VocaDbPvTaskException {
    /* ==  0. reading cmd == */
    log.info("configuring application");
    var appConfig = ConfigFactory.parse(args);

    /* == 1. read local task if exist == */
    log.info("reading local task file if exist");
    var taskFile = appConfig.userConfig.getTaskJsonFile();
    var taskFileReader = JacksonPojoTransformer.createWithDefaultMapper(VocaDbPvTask.class);
    // no need to check if it is directory, app config should handled it
    VocaDbPvTask task = getFromFile(taskFile, taskFileReader,
        e -> new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_009, "Invalid task json file", e));

    /* ==  2. read local ref if exist == */
    log.info("reading local reference file if exist");
    var refFile = appConfig.userConfig.getReferenceJsonFile();
    var refFileReader = JacksonPojoTransformer.createWithDefaultMapper(ResponseSongList.class);
    ResponseSongList ref = getFromFile(refFile, refFileReader,
        e -> new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_010, "Invalid ref json file", e));

    /* ==  3. update or create the task and the ref  == */
    log.info("updating or creating the task and the reference");
    var producer = new VocaDbTaskUpdater(
        appConfig.userConfig.getListId(),
        appConfig.userConfig.getUserAgent(),
        appConfig.systemConfig.getMaxResult());

    var updatedTaskRefPair = producer.createOrUpdate(task, ref, appConfig.userConfig.getTaskName());
    var updatedTask = updatedTaskRefPair.getOne();
    var updatedRef = updatedTaskRefPair.getTwo();

    /* ==  4. write back task and ref == */
    log.info("writing back the task json file");
    writeBack(taskFile, taskFileReader, updatedTask,
        "Printing out the task json in case we failed to update {}: \n{}",
        VocaDbPvTaskRCI.MIKU_TASK_011, "Fail to write or produce json for task");

    /* ==  5. write back ref == */
    log.info("writing back the reference json file");
    writeBack(refFile, refFileReader, updatedRef,
        "Printing out the reference json in case we failed to update {}: \n{}",
        VocaDbPvTaskRCI.MIKU_TASK_012, "Fail to write or produce json for reference");

    /* ==  6. finish == */
    log.info("終わった。( •̀ ω •́ )y");
  }

  /**
   * simply util function for getting pojo using {@link JacksonPojoTransformer}
   */
  private static <T> T getFromFile(
      Path jsonFile,
      JacksonPojoTransformer<T> taskFileReader,
      Function<IOException, VocaDbPvTaskException> expFunc) throws VocaDbPvTaskException {
    T pojo;
    if (Files.exists(jsonFile)){
      try {
        pojo = taskFileReader.read(jsonFile);
      } catch (IOException e){
        throw expFunc.apply(e);
      }
    } else {
      pojo = null;
    }
    return pojo;
  }

  /**
   * yet another simple util to write back to file
   * @param <T> the type of POJO
   */
  private static <T> void writeBack(
      Path file,
      JacksonPojoTransformer writer,
      T pojo,
      String warming,
      VocaDbPvTaskRCI rci,
      String error) throws VocaDbPvTaskException {
    try {
      var isFileExist = writer.write(pojo, file);
      if (!isFileExist) {
        log.warn(warming, file, writer.getMapper().writeValueAsString(pojo));
      }
    } catch (IOException e) {
      throw new VocaDbPvTaskException(rci, error, e);
    }
  }
}
