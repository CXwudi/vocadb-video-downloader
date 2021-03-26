package mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.mixin;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import com.sapher.youtubedl.callback.LineOutputCallback;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.BaseYoutubeDlDrivenDownloader;

/**
 * A pure youtube-dl downloader is the downloader that simply call {@link YoutubeDL#execute(YoutubeDLRequest, LineOutputCallback, LineOutputCallback)}
 * to finish the download precess.
 * @author CX无敌
 */
public interface PureYoutubeDlDownloadMixin extends BaseYoutubeDlDrivenDownloader {

  @Override
  default YoutubeDLResponse computeResponse(YoutubeDLRequest request) throws YoutubeDLException, InterruptedException {
    try {
      var log = org.slf4j.LoggerFactory.getLogger(PureYoutubeDlDownloadMixin.class);
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
