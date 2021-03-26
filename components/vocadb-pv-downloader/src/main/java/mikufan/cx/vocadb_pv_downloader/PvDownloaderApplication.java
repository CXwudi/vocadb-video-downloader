package mikufan.cx.vocadb_pv_downloader;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_downloader.controllers.MainRunner;
import mikufan.cx.vocadb_pv_downloader.util.MainUtil;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author CX无敌
 */
@Slf4j
@SpringBootApplication
public class PvDownloaderApplication extends MainUtil {
  public static void main(String[] args) throws VocaDbPvDlException {
    var context = SpringApplication.run(PvDownloaderApplication.class, args);
    context.getBean(MainRunner.class).run();
  }
}
