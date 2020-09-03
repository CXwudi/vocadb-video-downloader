package mikufan.cx.vocadb_pv_downloader.services.downloaders.root.mixin;

import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatus;
import mikufan.cx.vocadb_pv_downloader.services.downloaders.root.BaseRequestDownloadValidatePvDownloader;

import java.nio.file.Path;

/**
 * @author CX无敌
 */
public interface DefaultStatusValidationMixin<Req, Rsp> extends BaseRequestDownloadValidatePvDownloader<Req, Rsp>  {
  @Override
  //TODO: complete me
  default DownloadStatus validateStatus(String url, Path dir, String fileName, Rsp response, Throwable throwable) {
    var log = org.slf4j.LoggerFactory.getLogger(DefaultStatusValidationMixin.class);
    if (throwable != null){
      log.error("Exception in validateStatus:16", throwable);

    }

    return null;
  }
}
