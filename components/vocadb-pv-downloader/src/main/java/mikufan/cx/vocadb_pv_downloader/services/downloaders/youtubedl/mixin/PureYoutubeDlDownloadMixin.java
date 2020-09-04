package mikufan.cx.vocadb_pv_downloader.services.downloaders.youtubedl.mixin;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import mikufan.cx.vocadb_pv_downloader.services.downloaders.youtubedl.BaseYoutubeDlDrivenDownloader;

/**
 * @author CX无敌
 */
public interface PureYoutubeDlDownloadMixin extends BaseYoutubeDlDrivenDownloader {
  org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PureYoutubeDlDownloadMixin.class);

  @Override
  default YoutubeDLResponse computeResponse(YoutubeDLRequest request) throws InterruptedException, YoutubeDLException {
    try {
      return YoutubeDL.execute(request, log::info, log::info);
    } catch (YoutubeDLException e) {
      if (e.getCause() instanceof InterruptedException){
        throw (InterruptedException) e.getCause();
      } else {
        throw e;
      }
    }
  }
}
