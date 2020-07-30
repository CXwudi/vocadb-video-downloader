package mikufan.cx.vocadb_pv_task_producer;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_task_producer.config.parser.ConfigFactory;
import mikufan.cx.vocadb_pv_task_producer.main.ArtistFieldFixer;
import mikufan.cx.vocadb_pv_task_producer.main.ListFetcher;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;

import static mikufan.cx.vocadb_pv_task_producer.util.MainUtil.withHttpClient;

@Slf4j
public class Main {

  public static void main(String[] args) throws VocaDbPvTaskException {
    /* ==  0. reading cmd == */
    log.info("configuring application");
    var appConfig = ConfigFactory.parse(args);

    /* ==  the main logic == */
    var songList = withHttpClient(appConfig.userConfig.getUserAgent(),
        httpClient -> {
          /* ==  1. fetch list == */
          var listId = appConfig.getListId();
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
    /* ==  for each song in list == */
    for(var item : songList.getItems()){
      var song = item.getSong();

      /*
       * TODO: follow the rest of instruction in lucidchart UML diagram
       * 1. check if pv available
       *   1.1 if not, write to error dir
       *   1.2 if yes, write to output dir if not exist
       */
    }
  }

}
