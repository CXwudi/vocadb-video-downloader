package mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.mixin;

import com.sapher.youtubedl.YoutubeDLRequest;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.youtubedl.BaseYoutubeDlDrivenDownloader;

import java.nio.file.Path;

/**
 * specify the ffmpeg used by youtube-dl. <br/>
 * This is not same as using ffmpeg as the download engine
 * @author CX无敌
 */
public interface WithFFmpegMixin extends BaseYoutubeDlDrivenDownloader {

  @Override
  default YoutubeDLRequest buildRequest(String url, Path dir, String fileName){
    return new YoutubeDLRequest(url, dir.resolve(fileName).toAbsolutePath().toString())
        .setOption("--ffmpeg-location", getFFmpegPath());
  }

  String getFFmpegPath();
}
