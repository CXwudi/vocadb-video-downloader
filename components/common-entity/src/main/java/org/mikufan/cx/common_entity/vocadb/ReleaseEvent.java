package org.mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.eclipse.collections.api.list.MutableList;

@Getter
public class ReleaseEvent {

  @JsonProperty("date")
  private String date;

  @JsonProperty("urlSlug")
  private String urlSlug;

  @JsonProperty("venue")
  private Venue venue;

  @JsonProperty("endDate")
  private String endDate;

  @JsonProperty("description")
  private String description;

  @JsonProperty("mainPicture")
  private MainPicture mainPicture;

  @JsonProperty("songList")
  private SongList songList;

  @JsonProperty("version")
  private int version;

  @JsonProperty("seriesId")
  private int seriesId;

  @JsonProperty("seriesNumber")
  private int seriesNumber;

  @JsonProperty("tags")
  private MutableList<TagsItem> tags;

  @JsonProperty("venueName")
  private String venueName;

  @JsonProperty("names")
  private MutableList<NamesItem> names;

  @JsonProperty("artists")
  private MutableList<ArtistsItem> artists;

  @JsonProperty("series")
  private Series series;

  @JsonProperty("name")
  private String name;

  @JsonProperty("webLinks")
  private MutableList<WebLinksItem> webLinks;

  @JsonProperty("id")
  private int id;

  @JsonProperty("seriesSuffix")
  private String seriesSuffix;

  @JsonProperty("category")
  private String category;

  @JsonProperty("additionalNames")
  private String additionalNames;

  @JsonProperty("status")
  private String status;

  @Override
  public String toString() {
    return
        "ReleaseEvent{" +
            "date = '" + date + '\'' +
            ",urlSlug = '" + urlSlug + '\'' +
            ",venue = '" + venue + '\'' +
            ",endDate = '" + endDate + '\'' +
            ",description = '" + description + '\'' +
            ",mainPicture = '" + mainPicture + '\'' +
            ",songList = '" + songList + '\'' +
            ",version = '" + version + '\'' +
            ",seriesId = '" + seriesId + '\'' +
            ",seriesNumber = '" + seriesNumber + '\'' +
            ",tags = '" + tags + '\'' +
            ",venueName = '" + venueName + '\'' +
            ",names = '" + names + '\'' +
            ",artists = '" + artists + '\'' +
            ",series = '" + series + '\'' +
            ",name = '" + name + '\'' +
            ",webLinks = '" + webLinks + '\'' +
            ",id = '" + id + '\'' +
            ",seriesSuffix = '" + seriesSuffix + '\'' +
            ",category = '" + category + '\'' +
            ",additionalNames = '" + additionalNames + '\'' +
            ",status = '" + status + '\'' +
            "}";
  }
}