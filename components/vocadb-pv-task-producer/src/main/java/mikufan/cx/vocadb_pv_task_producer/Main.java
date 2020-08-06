package mikufan.cx.vocadb_pv_task_producer;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.project_vd_common_entity.failure.FailedSong;
import mikufan.cx.vocadb_pv_task_producer.config.parser.ConfigFactory;
import mikufan.cx.vocadb_pv_task_producer.main.ArtistFieldFixer;
import mikufan.cx.vocadb_pv_task_producer.main.ListFetcher;
import mikufan.cx.vocadb_pv_task_producer.main.SongInfoValidator;
import mikufan.cx.vocadb_pv_task_producer.main.SongInfoWriter;
import mikufan.cx.vocadb_pv_task_producer.util.MainUtil;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Main extends MainUtil {

  public static void main(String[] args) throws VocaDbPvTaskException {
    /* ==  0. reading cmd == */
    log.info("configuring application");
    var appConfig = ConfigFactory.parse(args);
    var listId = appConfig.getListId();

    /* ==  the main logic == */
    var songList = withHttpClient(appConfig.userConfig.getUserAgent(),
        httpClient -> {
          /* ==  1. fetch list == */
          log.info("start requesting full VocaDB songs list of id {}", listId);
          var listFetcher = new ListFetcher(appConfig.systemConfig.getMaxResult());
          var list = listFetcher.getVocadbListOfId(listId, httpClient);

          /* ==  2. fixing "various" "unknown" in artist fields in songs list == */
          log.info("song list of {} gotten, start processing artist field string fixup", listId);
          var artistFixer = new ArtistFieldFixer();
          artistFixer.fixAll(list, httpClient);

          return list;
        }
    );
    /* ==  for each song in list in parallel == */
    //preparation
    var validator = new SongInfoValidator();
    var songInfoWriter = new SongInfoWriter();
    var outputDir = appConfig.getOutputDir().toAbsolutePath();
    var errDir = outputDir.resolve("err");
    createDir(errDir);
    var threadPoolExecutor = new ThreadPoolExecutor(
        appConfig.systemConfig.getThreadCorePoolSize(),
        appConfig.systemConfig.getThreadMaxPoolSize(),
        Integer.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

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
