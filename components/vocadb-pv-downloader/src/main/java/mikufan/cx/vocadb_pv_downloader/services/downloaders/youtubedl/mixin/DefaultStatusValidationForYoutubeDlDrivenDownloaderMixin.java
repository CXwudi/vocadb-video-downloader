package mikufan.cx.vocadb_pv_downloader.services.downloaders.youtubedl.mixin;

import com.sapher.youtubedl.YoutubeDLResponse;
import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatus;
import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatusEnum;
import mikufan.cx.vocadb_pv_downloader.services.downloaders.youtubedl.BaseYoutubeDlDrivenDownloader;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author CX无敌
 */
public interface DefaultStatusValidationForYoutubeDlDrivenDownloaderMixin extends BaseYoutubeDlDrivenDownloader {
  org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PureYoutubeDlDownloadMixin.class);
  String ERROR = "ERROR";
  // unfortunately we can't put private on interface

  //TODO: complete me
  @Override
  default DownloadStatus validateStatus(String url, Path dir, String fileName, YoutubeDLResponse response, Throwable throwable) {
    // this means in the previous method, YoutubeDL.execute() failed
    if (response == null){
      return new DownloadStatus(
          DownloadStatusEnum.FAIL_INITIAL,
          String.format("Fail to finish executing youtube-dl request on %s to %s", url, fileName));
    }

    if (!response.isSuccess()){
      log.warn("Youtube-dl return with exit code {} when downloading {}", response.getExitCode(), fileName);
    }

    //checking
    var expectedFile = dir.resolve(fileName);
    if (Files.notExists(expectedFile)){
      log.warn("Oh no, can not find the downloaded PV in {}", expectedFile.toAbsolutePath());
      return new DownloadStatus(DownloadStatusEnum.UNKNOWN_STATUS,
          "download completed but can not find the downloaded PV in " + expectedFile.toAbsolutePath());
    }

    var stdOut = response.getOut();
    var stdErr = response.getErr();
    int indexOfPercentage = stdOut.lastIndexOf('%');
    double progress = Double.parseDouble(stdOut.substring(indexOfPercentage - 4, indexOfPercentage).strip());
    if (progress < 100 || stdErr.contains(ERROR)){
      log.warn("What? this PV {} doesn't finish downloading", fileName);
    }

    return new DownloadStatus(DownloadStatusEnum.SUCCESS, null);
  }
}
