package mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.mixin;

import com.sapher.youtubedl.YoutubeDLResponse;
import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatus;
import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatusEnum;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.BaseYoutubeDlDrivenDownloader;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;

/**
 * @author CX无敌
 */
public interface YoutubeDlProgressCheckOptionalMixin extends BaseYoutubeDlDrivenDownloader {
  // unfortunately we can't put private on interface

  @Override
  default DownloadStatus validateStatus(String url, Path dir, String fileName, YoutubeDLResponse response, Exception exp) {
//    var nullCheck = DefaultNullCheckMixin.super.validateStatus(url, dir, fileName, response, exp);
//    if (DownloadStatusEnum.isFailed(nullCheck.getStatus())){
//      return nullCheck;
//    }
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(YoutubeDlProgressCheckOptionalMixin.class);

    var stdOut = response.getOut();
    int indexOfPercentage = stdOut.lastIndexOf('%');
    if (indexOfPercentage != -1) {
      //there exist a '%', can be used to check progress
      // but not all downloader have '%', e.g. ffmpeg downloader
      var percentageStr = stdOut.substring(indexOfPercentage - 4, indexOfPercentage).strip();
      if (StringUtils.isNumeric(percentageStr)){
        double progress = Double.parseDouble(percentageStr);
        if (progress < 100){
          log.warn("What? this PV {} stopped downloading at {} percentage", fileName, progress);
          return new DownloadStatus(DownloadStatusEnum.FAIL_DOWNLOAD,
              "unfinished downloading stop at " + progress + "%");
        }
      } else {
        log.warn("A wired '%' is found after a string '{}', was expecting a numerical string", percentageStr);
      }
    } else {
      log.info("No '%' found in stdout, skip checking progress");
    }
    return new DownloadStatus(DownloadStatusEnum.SUCCESS, null);
//    var fileExistenceCheck = DefaultFileExistenceCheckMixin.super.validateStatus(url, dir, fileName, response, exp);
//    if (DownloadStatusEnum.isFailed(fileExistenceCheck.getStatus())){
//      return fileExistenceCheck;
//    } else {
//      return new DownloadStatus(DownloadStatusEnum.SUCCESS, null);
//    }
  }
}
