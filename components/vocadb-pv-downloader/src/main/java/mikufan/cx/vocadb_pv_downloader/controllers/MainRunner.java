package mikufan.cx.vocadb_pv_downloader.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_downloader.config.entity.AppConfig;
import mikufan.cx.vocadb_pv_downloader.util.MainUtil;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;

/**
 * @author CX无敌
 */
@Controller @Slf4j
@RequiredArgsConstructor
public class MainRunner extends MainUtil {
  private final ObjectProvider<AppConfig> configGetter;

  public void run() throws VocaDbPvDlException {

    var appConfig = configGetter.getObject();
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
