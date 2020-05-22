package mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ArtistsItem {

  @JsonProperty("artist")
  private Artist artist;

  @JsonProperty("isSupport")
  private boolean isSupport;

  @JsonProperty("isCustomName")
  private boolean isCustomName;

  @JsonProperty("roles")
  private String roles;

  @JsonProperty("name")
  private String name;

  @JsonProperty("effectiveRoles")
  private String effectiveRoles;

  @JsonProperty("categories")
  private String categories;

  @JsonProperty("id")
  private int id;

  @Override
  public String toString() {
    return
        "ArtistsItem{" +
            "artist = '" + artist + '\'' +
            ",isSupport = '" + isSupport + '\'' +
            ",isCustomName = '" + isCustomName + '\'' +
            ",roles = '" + roles + '\'' +
            ",name = '" + name + '\'' +
            ",effectiveRoles = '" + effectiveRoles + '\'' +
            ",categories = '" + categories + '\'' +
            ",id = '" + id + '\'' +
            "}";
  }
}