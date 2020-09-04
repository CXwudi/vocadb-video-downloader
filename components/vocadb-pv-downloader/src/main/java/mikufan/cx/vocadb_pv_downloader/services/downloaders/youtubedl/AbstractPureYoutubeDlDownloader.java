package mikufan.cx.vocadb_pv_downloader.services.downloaders.youtubedl;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.callback.LineOutputCallback;
import mikufan.cx.vocadb_pv_downloader.services.downloaders.youtubedl.mixin.DefaultStatusValidationForYoutubeDlDrivenDownloaderMixin;
import mikufan.cx.vocadb_pv_downloader.services.downloaders.youtubedl.mixin.PureYoutubeDlDownloadMixin;

/**
 * Define how purely youtube-dl driven downloader is behaved. <br/>
 * A purely youtube-dl downloader is the downloader that simply call {@link YoutubeDL#execute(YoutubeDLRequest, LineOutputCallback, LineOutputCallback)}
 * to finish the download precess.
 * @author CX无敌
 */
public interface AbstractPureYoutubeDlDownloader
    extends BaseYoutubeDlDrivenDownloader, PureYoutubeDlDownloadMixin, DefaultStatusValidationForYoutubeDlDrivenDownloaderMixin {


}
