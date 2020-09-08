package mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl;


import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatus;
import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatusSaver;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.root.BaseRequestDownloadValidatePvDownloader;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.root.mixin.DefaultFileExistenceCheckMixin;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.root.mixin.DefaultNullCheckMixin;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.mixin.PureYoutubeDlDownloadMixin;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.mixin.YoutubeDlProgressCheckOptionalMixin;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.mixin.YoutubeDlReturnCodeCheckMixin;

import java.nio.file.Path;

/**
 * Define how pure youtube-dl driven downloader is behaved. <br/>
 * Only method that is not implemented is {@link BaseRequestDownloadValidatePvDownloader#buildRequest(String, Path, String)}
 * @author CX无敌
 */
public interface AbstractPureYoutubeDlDownloader
    extends BaseYoutubeDlDrivenDownloader, PureYoutubeDlDownloadMixin,
    DefaultNullCheckMixin<YoutubeDLRequest, YoutubeDLResponse>,
    DefaultFileExistenceCheckMixin<YoutubeDLRequest, YoutubeDLResponse>,
    YoutubeDlProgressCheckOptionalMixin,
    YoutubeDlReturnCodeCheckMixin {


  /**
   * as following the description from {@link BaseRequestDownloadValidatePvDownloader#validateStatus(String, Path, String, Object, Exception)},
   * run multiple validation in order.
   *
   * @param url pv url
   * @param dir where the pv file is saved
   * @param fileName the file of the pv
   * @param response the response computed from {@link BaseRequestDownloadValidatePvDownloader#computeResponse(Object)}.
   *                 It could be null, especially when the computation abort abnormally.
   * @param exp the exception thrown from {@link BaseRequestDownloadValidatePvDownloader#computeResponse(Object)} if exist.
   * @return
   */
  @Override
  default DownloadStatus validateStatus(String url, Path dir, String fileName, YoutubeDLResponse response, Exception exp) {
    var statusSaver = new DownloadStatusSaver(4);
    statusSaver.add(DefaultNullCheckMixin.super.validateStatus(url, dir, fileName, response, exp));
    statusSaver.add(YoutubeDlReturnCodeCheckMixin.super.validateStatus(url, dir, fileName, response, exp));
    statusSaver.add(YoutubeDlProgressCheckOptionalMixin.super.validateStatus(url, dir, fileName, response, exp));
    statusSaver.add(DefaultFileExistenceCheckMixin.super.validateStatus(url, dir, fileName, response, exp));
    return statusSaver.getBestRepresentedStatus();
  }
}
