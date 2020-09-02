package mikufan.cx.vocadb_pv_task_producer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.project_vd_common_entity.failure.FailedSong;
import mikufan.cx.vocadb_pv_task_producer.services.ArtistFieldFixer;
import mikufan.cx.vocadb_pv_task_producer.services.ListFetcher;
import mikufan.cx.vocadb_pv_task_producer.services.SongInfoValidator;
import mikufan.cx.vocadb_pv_task_producer.services.SongInfoWriter;
import mikufan.cx.vocadb_pv_task_producer.services.config.ConfigFactory;
import mikufan.cx.vocadb_pv_task_producer.util.MainRunnerUtil;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;

/**
 * @author CX无敌
 */
@Controller @Slf4j
@RequiredArgsConstructor
public class MainRunner extends MainRunnerUtil {

  private final ObjectProvider<ConfigFactory> configFactoryOp;
  private final ObjectProvider<ListFetcher> listFetcherOp;
  private final ObjectProvider<ArtistFieldFixer> artistFieldFixerOp;

  private final ObjectProvider<SongInfoValidator> songInfoValidatorOp;
  private final ObjectProvider<SongInfoWriter> songInfoWriterOp;

  /**
   * Callback used to run the bean.
   *
   * @param args incoming main method arguments
   * @throws Exception on error
   */
  public void run(String... args) throws VocaDbPvTaskException {
    /* ==  0. reading cmd == */
    log.info("configuring application");
    var appConfig = configFactoryOp.getObject().parse(args);
    var listId = appConfig.getListId();

    /* ==  the main logic == */
    var songList = withHttpClient(appConfig.userConfig.getUserAgent(),
        httpClient -> {
          /* ==  1. fetch list == */
          log.info("start requesting full VocaDB songs list of id {}", listId);
          var listFetcher = listFetcherOp.getObject(appConfig.systemConfig.getMaxResult());
          var list = listFetcher.getVocadbListOfId(listId, httpClient);

          /* ==  2. fixing "various" "unknown" in artist fields in songs list == */
          log.info("song list of {} gotten, start processing artist field string fixup", listId);
          var artistFixer = artistFieldFixerOp.getObject();
          artistFixer.fixAll(list, httpClient);

          return list;
        }
    );
    /* ==  for each song in list in parallel == */
    //preparation
    var validator = songInfoValidatorOp.getObject();
    var songInfoWriter = songInfoWriterOp.getObject();
    var outputDir = appConfig.getOutputDir().toAbsolutePath();
    var errDir = outputDir.resolve(appConfig.systemConfig.getErrDir());
    createDir(errDir);
    var threadPoolExecutor = createThreadPoolExecutor(
        appConfig.systemConfig.getThreadCorePoolSize(),
        appConfig.systemConfig.getThreadMaxPoolSize());

    log.info("start writing song info in list {} to output folder {}", listId, outputDir);
    songList.getItems()
        .asParallel(threadPoolExecutor, appConfig.systemConfig.getBatchSize())
        .forEach(makeThrowableProcedure(item -> {

          var song = item.getSong();
          var name = song.getName();

          /* == 3. validate if the song is downloadable == */
          log.info("validating PVs info of {}", name);
          var failedReasonOpt = validator.validate(song);

          /* == 4. base on result, write to specific folder == */
          if (failedReasonOpt.isPresent()) {
            var failedSong = new FailedSong(song, failedReasonOpt.get());
            songInfoWriter.writeError(failedSong, errDir);
          } else {
            songInfoWriter.writeSongInfo(song, outputDir);
          }
        }));

    /* == 5. all finished == */
    threadPoolExecutor.shutdown();
    log.info("終わった。( •̀ ω •́ )y");
  }
}
