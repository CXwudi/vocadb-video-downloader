package mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.mixin;

import com.sapher.youtubedl.YoutubeDLResponse;
import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatus;
import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatusEnum;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.BaseYoutubeDlDrivenDownloader;

import java.nio.file.Path;

/**
 * @author CX无敌
 */
public interface YoutubeDlReturnCodeCheckMixin extends
    BaseYoutubeDlDrivenDownloader{

  @Override
  default DownloadStatus validateStatus(String url, Path dir, String fileName, YoutubeDLResponse response, Exception exp){
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(YoutubeDlReturnCodeCheckMixin.class);
    var stdErr = response.getErr();
    var exitCode = response.getExitCode();

    if (!response.isSuccess()){
      log.warn("Received signal {}", exitCode);
      return new DownloadStatus(DownloadStatusEnum.FAIL_DOWNLOAD, "Download failed with exit code " + exitCode
          + ". stderr = \n" + stdErr);
    }
    return new DownloadStatus(DownloadStatusEnum.SUCCESS, "youtube-dl exits with code " + exitCode);

  }
}
