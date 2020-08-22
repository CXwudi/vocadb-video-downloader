package mikufan.cx.vocadb_pv_downloader;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_downloader.config.ConfigFactory;
import mikufan.cx.vocadb_pv_downloader.util.MainUtil;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;
import org.apache.commons.lang3.SystemUtils;

/**
 * @author CX无敌
 */
@Slf4j
public class Main extends MainUtil {
  public static void main(String[] args) throws VocaDbPvDlException {
    var appConfig = new ConfigFactory().getConfig(args);
    log.info("pref = {}", appConfig.userConfig.getPvPreference());
    log.info("ytdl = {}", appConfig.userConfig.getYoutubeDlFile());
    log.info("ffmpeg = {}", appConfig.userConfig.getFfmpegFile());

    log.info("ytdl-win = {}", appConfig.systemConfig.getYoutubeDlWinPath().toAbsolutePath());
    log.info("ytdl-linux = {}", appConfig.systemConfig.getYoutubeDlLinuxPath().toAbsolutePath());

    log.info("win os = {}", SystemUtils.IS_OS_WINDOWS);
    log.info("mac os = {}", SystemUtils.IS_OS_MAC);
    log.info("linux os = {}", SystemUtils.IS_OS_LINUX);

    log.info("downloader config = {}", appConfig.userConfig.getDownloaderConfigs());
  }
}
