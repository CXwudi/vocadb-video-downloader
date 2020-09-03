package mikufan.cx.vocadb_pv_downloader.services.config.downloader;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author CX无敌
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "config type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = MetaYoutubeDlConfiguration.class, name = "youtube-dl config")
})
public abstract class AbstractDownloaderConfiguration {

  /**
   * should be the class name of the downloader that is been created via reflection
   */
  @JsonProperty
  private String targetDownloaderName;

}
