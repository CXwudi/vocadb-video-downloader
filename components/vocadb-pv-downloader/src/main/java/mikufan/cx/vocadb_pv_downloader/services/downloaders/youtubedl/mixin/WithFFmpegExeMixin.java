package mikufan.cx.vocadb_pv_downloader.services.downloaders.youtubedl.mixin;

import com.sapher.youtubedl.YoutubeDLRequest;
import mikufan.cx.vocadb_pv_downloader.services.downloaders.youtubedl.base.BaseYoutubeDlDrivenDownloader;

import java.nio.file.Path;

/**
 * @author CX无敌
 */
public interface WithFFmpegExeMixin extends BaseYoutubeDlDrivenDownloader {

  @Override
  default YoutubeDLRequest buildRequest(String url, Path dir, String fileName){
    return new YoutubeDLRequest(url, dir.resolve(fileName).toAbsolutePath().toString())
        .setOption("--ffmpeg-location", getFFmpegPath());
  }

  String getFFmpegPath();
}
