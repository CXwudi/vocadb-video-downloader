package mikufan.cx.vocadb_pv_downloader.services.downloaders.root.mixin;

import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatus;
import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatusEnum;
import mikufan.cx.vocadb_pv_downloader.services.downloaders.root.BaseRequestDownloadValidatePvDownloader;

import java.nio.file.Path;

/**
 * Default check to make sure
 * @author CX无敌
 */
public interface DefaultNullCheckMixin<Req, Rsp> extends BaseRequestDownloadValidatePvDownloader<Req, Rsp>  {
  @Override

  default DownloadStatus validateStatus(String url, Path dir, String fileName, Rsp response, Exception exp) {
    if (exp != null){
      return new DownloadStatus(DownloadStatusEnum.FAIL_DOWNLOAD,
          "Exception happens during download " + fileName + " from " + url + " with msg: " + exp.getMessage());
    } else if (response == null){
      return new DownloadStatus(DownloadStatusEnum.FAIL_DOWNLOAD,
          "No response object returned from downloading process during download " + fileName + " from " + url);
    }

    return new DownloadStatus(DownloadStatusEnum.SUCCESS, "Base check from DefaultNullCheckMixin success");
  }
}
