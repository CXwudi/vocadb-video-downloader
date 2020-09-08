package mikufan.cx.vocadb_pv_downloader.services.downloaders.youtubedl;

import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import mikufan.cx.vocadb_pv_downloader.services.downloaders.root.BaseRequestDownloadValidatePvDownloader;

/**
 * Base downloader that use {@link YoutubeDLRequest} and {@link YoutubeDLResponse}
 * as the request and response object. <br/>
 *
 * <h3>Implementation Notice:</h3>
 * Subclasses may not purely use youtube-dl as a downloading engine,
 * they could, for example, use IDM to download the real PV url extracted from youtube-dl. <br/>
 *
 *
 * @author CX无敌
 */
public interface BaseYoutubeDlDrivenDownloader extends
    BaseRequestDownloadValidatePvDownloader<YoutubeDLRequest, YoutubeDLResponse>
{

}
