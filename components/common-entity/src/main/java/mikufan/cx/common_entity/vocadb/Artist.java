package mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Artist {

  @JsonProperty("pictureMime")
  private String pictureMime;

  @JsonProperty("deleted")
  private boolean deleted;

  @JsonProperty("releaseDate")
  private String releaseDate;

  @JsonProperty("name")
  private String name;

  @JsonProperty("artistType")
  private String artistType;

  @JsonProperty("id")
  private int id;

  @JsonProperty("version")
  private int version;

  @JsonProperty("additionalNames")
  private String additionalNames;

  @JsonProperty("status")
  private String status;

  @Override
  public String toString() {
    return
        "Artist{" +
            "pictureMime = '" + pictureMime + '\'' +
            ",deleted = '" + deleted + '\'' +
            ",releaseDate = '" + releaseDate + '\'' +
            ",name = '" + name + '\'' +
            ",artistType = '" + artistType + '\'' +
            ",id = '" + id + '\'' +
            ",version = '" + version + '\'' +
            ",additionalNames = '" + additionalNames + '\'' +
            ",status = '" + status + '\'' +
            "}";
  }
}