package mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.mixin;

import com.sapher.youtubedl.YoutubeDLRequest;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.BaseYoutubeDlDrivenDownloader;

import java.nio.file.Path;

/**
 * add --write-thumbnail to the youtube-dl option
 * @author CX无敌
 */
public interface WithThumbnailRequestMixin extends BaseYoutubeDlDrivenDownloader {

  @Override
  default YoutubeDLRequest buildRequest(String url, Path dir, String fileName) {
    return new YoutubeDLRequest(url, dir.resolve(fileName).toAbsolutePath().toString())
        .setOption("--write-thumbnail");
  }
}
