package mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.eclipse.collections.api.list.MutableList;

@Getter
public class Series {

  @JsonProperty("pictureMime")
  private String pictureMime;

  @JsonProperty("urlSlug")
  private String urlSlug;

  @JsonProperty("deleted")
  private boolean deleted;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("webLinks")
  private MutableList<WebLinksItem> webLinks;

  @JsonProperty("id")
  private int id;

  @JsonProperty("category")
  private String category;

  @JsonProperty("version")
  private int version;

  @JsonProperty("additionalNames")
  private String additionalNames;

  @JsonProperty("status")
  private String status;

  @Override
  public String toString() {
    return
        "Series{" +
            "pictureMime = '" + pictureMime + '\'' +
            ",urlSlug = '" + urlSlug + '\'' +
            ",deleted = '" + deleted + '\'' +
            ",name = '" + name + '\'' +
            ",description = '" + description + '\'' +
            ",webLinks = '" + webLinks + '\'' +
            ",id = '" + id + '\'' +
            ",category = '" + category + '\'' +
            ",version = '" + version + '\'' +
            ",additionalNames = '" + additionalNames + '\'' +
            ",status = '" + status + '\'' +
            "}";
  }
}