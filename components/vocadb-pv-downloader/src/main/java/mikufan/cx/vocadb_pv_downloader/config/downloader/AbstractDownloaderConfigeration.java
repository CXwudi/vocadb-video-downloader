package mikufan.cx.vocadb_pv_downloader.config.downloader;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.NoArgsConstructor;

/**
 * @author CX无敌
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, property = "_type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = MetaYoutubeDlConfiguration.class, name = "youtube-dl config")
})
@NoArgsConstructor
public abstract class AbstractDownloaderConfigeration {

}
