package mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Tag {

  @JsonProperty("urlSlug")
  private String urlSlug;

  @JsonProperty("name")
  private String name;

  @JsonProperty("id")
  private int id;

  @JsonProperty("categoryName")
  private String categoryName;

  @JsonProperty("additionalNames")
  private String additionalNames;

  @Override
  public String toString() {
    return
        "Tag{" +
            "urlSlug = '" + urlSlug + '\'' +
            ",name = '" + name + '\'' +
            ",id = '" + id + '\'' +
            ",categoryName = '" + categoryName + '\'' +
            ",additionalNames = '" + additionalNames + '\'' +
            "}";
  }
}