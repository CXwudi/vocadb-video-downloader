package mikufan.cx.vocadb_pv_downloader.services.config.downloader;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.eclipse.collections.api.map.ImmutableMap;

import java.nio.file.Path;

/**
 * Any possible things related to youtube-dl config.
 * Keep it for recording raw input only.
 * @author CX无敌
 */
@Getter @ToString
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class MetaYoutubeDlConfiguration extends AbstractDownloaderConfiguration {
  /**
   * the youtube-dl key-value pair options <br/>
   * user should not put url, -o into here as our program already handles it
   */
  @JsonProperty
  private ImmutableMap<String, String> options;

  /**
   * optional, youtube-dl path
   */
  @JsonProperty
  private Path youtubeDlPath;

  /**
   * optional, ffmpeg path, some downloader might require this
   */
  @JsonProperty
  private Path ffmpegPath;

  /**
   * optional, idm path, some downloader might require this
   */
  @JsonProperty
  private Path idmPath;

}
