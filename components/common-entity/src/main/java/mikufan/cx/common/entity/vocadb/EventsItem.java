package mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EventsItem {

  @JsonProperty("date")
  private String date;

  @JsonProperty("urlSlug")
  private String urlSlug;

  @JsonProperty("venue")
  private Venue venue;

  @JsonProperty("endDate")
  private String endDate;

  @JsonProperty("hasVenueOrVenueName")
  private boolean hasVenueOrVenueName;

  @JsonProperty("description")
  private String description;

  @JsonProperty("customName")
  private boolean customName;

  @JsonProperty("songList")
  private SongList songList;

  @JsonProperty("version")
  private int version;

  @JsonProperty("pictureMime")
  private String pictureMime;

  @JsonProperty("venueName")
  private String venueName;

  @JsonProperty("deleted")
  private boolean deleted;

  @JsonProperty("series")
  private Series series;

  @JsonProperty("name")
  private String name;

  @JsonProperty("id")
  private int id;

  @JsonProperty("category")
  private String category;

  @JsonProperty("inheritedCategory")
  private String inheritedCategory;

  @JsonProperty("additionalNames")
  private String additionalNames;

  @JsonProperty("status")
  private String status;

  @Override
  public String toString() {
    return
        "EventsItem{" +
            "date = '" + date + '\'' +
            ",urlSlug = '" + urlSlug + '\'' +
            ",venue = '" + venue + '\'' +
            ",endDate = '" + endDate + '\'' +
            ",hasVenueOrVenueName = '" + hasVenueOrVenueName + '\'' +
            ",description = '" + description + '\'' +
            ",customName = '" + customName + '\'' +
            ",songList = '" + songList + '\'' +
            ",version = '" + version + '\'' +
            ",pictureMime = '" + pictureMime + '\'' +
            ",venueName = '" + venueName + '\'' +
            ",deleted = '" + deleted + '\'' +
            ",series = '" + series + '\'' +
            ",name = '" + name + '\'' +
            ",id = '" + id + '\'' +
            ",category = '" + category + '\'' +
            ",inheritedCategory = '" + inheritedCategory + '\'' +
            ",additionalNames = '" + additionalNames + '\'' +
            ",status = '" + status + '\'' +
            "}";
  }
}