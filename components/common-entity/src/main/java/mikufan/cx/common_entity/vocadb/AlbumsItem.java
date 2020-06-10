package mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AlbumsItem {

  @JsonProperty("coverPictureMime")
  private String coverPictureMime;

  @JsonProperty("releaseDate")
  private ReleaseDate releaseDate;

  @JsonProperty("artistString")
  private String artistString;

  @JsonProperty("ratingCount")
  private int ratingCount;

  @JsonProperty("version")
  private int version;

  @JsonProperty("deleted")
  private boolean deleted;

  @JsonProperty("discType")
  private String discType;

  @JsonProperty("name")
  private String name;

  @JsonProperty("id")
  private int id;

  @JsonProperty("additionalNames")
  private String additionalNames;

  @JsonProperty("releaseEvent")
  private ReleaseEvent releaseEvent;

  @JsonProperty("createDate")
  private String createDate;

  @JsonProperty("ratingAverage")
  private int ratingAverage;

  @JsonProperty("status")
  private String status;

  @Override
  public String toString() {
    return
        "AlbumsItem{" +
            "coverPictureMime = '" + coverPictureMime + '\'' +
            ",releaseDate = '" + releaseDate + '\'' +
            ",artistString = '" + artistString + '\'' +
            ",ratingCount = '" + ratingCount + '\'' +
            ",version = '" + version + '\'' +
            ",deleted = '" + deleted + '\'' +
            ",discType = '" + discType + '\'' +
            ",name = '" + name + '\'' +
            ",id = '" + id + '\'' +
            ",additionalNames = '" + additionalNames + '\'' +
            ",releaseEvent = '" + releaseEvent + '\'' +
            ",createDate = '" + createDate + '\'' +
            ",ratingAverage = '" + ratingAverage + '\'' +
            ",status = '" + status + '\'' +
            "}";
  }
}