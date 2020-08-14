package mikufan.cx.vocadb_pv_downloader.config.downloader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.collections.api.map.MutableMap;

import java.nio.file.Path;

/**
 * Any possible things related to youtube-dl config.
 * Keep it for recording raw input only.
 * @author CX无敌
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MetaYoutubeDlConfiguration extends AbstractDownloaderConfigeration {
  /**
   * the youtube-dl key-value pair options <br/>
   * user should not put url, -o into here as our program already handles it
   */
  private MutableMap<String, String> options;

  /**
   * optional youtube-dl path
   */
  private Path youtubeDlPath;

  /**
   * optional youtube-dl path
   */
  private Path ffmpegPath;

  /**
   * optional youtube-dl path
   */
  private Path idmPath;

}
