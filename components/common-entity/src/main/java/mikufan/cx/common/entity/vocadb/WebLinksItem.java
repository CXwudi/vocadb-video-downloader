package mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class WebLinksItem {

  @JsonProperty("descriptionOrUrl")
  private String descriptionOrUrl;

  @JsonProperty("description")
  private String description;

  @JsonProperty("id")
  private int id;

  @JsonProperty("category")
  private String category;

  @JsonProperty("url")
  private String url;

  @Override
  public String toString() {
    return
        "WebLinksItem{" +
            "descriptionOrUrl = '" + descriptionOrUrl + '\'' +
            ",description = '" + description + '\'' +
            ",id = '" + id + '\'' +
            ",category = '" + category + '\'' +
            ",url = '" + url + '\'' +
            "}";
  }
}