package mikufan.cx.vocadb_pv_downloader.config.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import mikufan.cx.project_vd_common_util.pv_service.SupportedPvServices;
import mikufan.cx.vocadb_pv_downloader.config.downloader.AbstractDownloaderConfigeration;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;

import java.nio.file.Path;

/**
 * Immutable user config parsed from yaml file. Allows null in optional fields.
 * All null fields should be picked up with default value lazily.
 * @author CX无敌
 */
@Getter @ToString
public class UserConfig {

  /**
   * optional, user's own preference of choosing the best available service that
   * has the pv available for downloading. if non, we use {@link SupportedPvServices#getSupportedPvServices()}
   * as the default preference
   */
  @JsonProperty
  private ImmutableList<String> pvPreference;

  /**
   * optional, user defined youtube-dl file
   * if non, the system uses our own youtube-dl base on os type
   */
  @JsonProperty
  private Path youtubeDlFile;

  /**
   * optional, user defined ffmpeg file that is used for youtube-dl.
   * if non, using Jave2's embedded ffmpeg
   */
  @JsonProperty
  private Path ffmpegFile;

  // using MutableList inside the map because of the bug in jackson https://github.com/FasterXML/jackson-datatypes-collections/issues/71
  /**
   * required, where key value is the name of the supported Pv services,
   * and value value is the configuration
   */
  @JsonProperty
  private ImmutableMap<String, MutableList<AbstractDownloaderConfigeration>> downloaderConfigs;

}
