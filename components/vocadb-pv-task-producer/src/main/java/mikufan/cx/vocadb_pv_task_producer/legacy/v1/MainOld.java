package mikufan.cx.vocadb_pv_task_producer.legacy.v1;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.project_vd_common_util.exception.ThrowableFunction;
import mikufan.cx.project_vd_common_util.io.JacksonPojoTransformer;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Meta main class, it shows all procedures
 * @author CX无敌
 */
@Slf4j
public class MainOld {
  public static void main(String[] args) throws VocaDbPvTaskException {
//    /* ==  0. reading cmd == */
//    log.info("configuring application");
//    var appConfig = ConfigFactory.parse(args);
//
//    /* == 1. read local task if exist == */
//    log.info("reading local task file if exist");
//    var taskFile = appConfig.userConfig.getTaskJsonFile();
//    var taskFileReader = JacksonPojoTransformer.createWithDefaultMapper(VocaDbPvTask.class);
//    // no need to check if it is directory, app config should handled it
//    VocaDbPvTask task = getFromFile(taskFile, taskFileReader,
//        VocaDbPvTaskRCI.MIKU_TASK_009, "Invalid task json file");
//
//    /* ==  2. read local ref if exist == */
//    log.info("reading local reference file if exist");
//    var refFile = appConfig.userConfig.getReferenceJsonFile();
//    var refFileReader = JacksonPojoTransformer.createWithDefaultMapper(PartialSongList.class);
//    PartialSongList ref = getFromFile(refFile, refFileReader,
//        VocaDbPvTaskRCI.MIKU_TASK_010, "Invalid ref json file");
//
//    // MainOld Body Part //
//    var updatedTaskRefPair = withHttpClient(appConfig.userConfig.getUserAgent(), httpClient -> {
//      /* ==  3. get full VocaDB song list  == */
//      var listId = appConfig.userConfig.getListId();
//      log.info("start requesting full VocaDB songs list of id {}", listId);
//      var listFetcher = new ListFetcher(appConfig.systemConfig.getMaxResult());
//      var songList = listFetcher.getVocadbListOfId(listId, httpClient);
//
//      /* ==  4. fixing "various" "unknown" in artist fields in songs list  == */
//      log.info("song list of {} gotten, start processing artist field string fixup", listId);
//      var artistFixer = new ArtistFieldFixer();
//      artistFixer.fixAll(songList, httpClient);
//
//      /* ==  5. merging the new song list into task and ref  == */
//      log.info("merging songs list into task and reference");
//      var merger = new ListTaskMerger();
//      var mergedTask = merger.mergeToTask(task, songList, appConfig.userConfig.getTaskName(), appConfig.userConfig.getPvPerfOrd());
//      var mergedRef = merger.mergeToList(ref, songList);
//
//      return Tuples.pair(mergedTask, mergedRef);
//    });
//
//    var updatedTask = updatedTaskRefPair.getOne();
//    var updatedRef = updatedTaskRefPair.getTwo();
//
//    /* ==  6. write back task == */
//    log.info("writing back the task json file");
//    writeBack(taskFile, taskFileReader, updatedTask,
//        "Printing out the task json in case we failed to update {}: \n{}",
//        VocaDbPvTaskRCI.MIKU_TASK_011, "Fail to write or produce json for task");
//
//    /* ==  7. write back ref == */
//    log.info("writing back the reference json file");
//    writeBack(refFile, refFileReader, updatedRef,
//        "Printing out the reference json in case we failed to update {}: \n{}",
//        VocaDbPvTaskRCI.MIKU_TASK_012, "Fail to write or produce json for reference");
//
//    /* ==  8. finish == */
//    log.info("終わった。( •̀ ω •́ )y");
  }

  /**
   * simply util function for getting pojo using {@link JacksonPojoTransformer}
   */
  private static <T> T getFromFile(
      Path jsonFile,
      JacksonPojoTransformer<T> taskFileReader,
      VocaDbPvTaskRCI rci,
      String error) throws VocaDbPvTaskException {
    T pojo;
    if (Files.exists(jsonFile)){
      try {
        pojo = taskFileReader.read(jsonFile);
      } catch (IOException e){
        throw new VocaDbPvTaskException(rci, error, e);
      }
    } else {
      pojo = null;
    }
    return pojo;
  }

  /**
   * Warp a piece of code under the existence of a http client
   * @param userAgent user agent that the http client used
   * @param withFunction the function to be called with the input of the created http client
   * @return whatever withFunction returns
   * @throws VocaDbPvTaskException whatever {@link VocaDbPvTaskException} that withFunction thrown or this function thrown
   */
  private static <T> T withHttpClient(String userAgent, ThrowableFunction<CloseableHttpClient, T> withFunction) throws VocaDbPvTaskException {
    try (var httpClient = HttpClients.custom()
        .setUserAgent(userAgent)
        .build()){
      log.debug("universal http client created with user-agent {}", userAgent);
      return withFunction.toFunction().apply(httpClient);
    } catch (IOException e){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_301, "Fail to handle HttpClient closable resource", e);
    }
  }

  /**
   * yet another simple util to write back to file
   * @param <T> the type of POJO
   */
  private static <T> void writeBack(
      Path file,
      JacksonPojoTransformer<T> writer,
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
