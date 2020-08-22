package mikufan.cx.vocadb_pv_downloader.config.downloader;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author CX无敌
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "config type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = MetaYoutubeDlConfiguration.class, name = "youtube-dl config")
})
@Getter
@AllArgsConstructor
public abstract class AbstractDownloaderConfigeration {

  /**
   * should be the class name of the downloader that is been created via reflection
   */
  @JsonProperty
  private String targetDownloaderName;

}
