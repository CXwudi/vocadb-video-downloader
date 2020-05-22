package mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.eclipse.collections.api.list.MutableList;

@Getter
public class Song {

  @JsonProperty("albums")
  private MutableList<AlbumsItem> albums;

  @JsonProperty("publishDate")
  private String publishDate;

  @JsonProperty("mainPicture")
  private MainPicture mainPicture;

  @JsonProperty("artistString")
  private String artistString;

  @JsonProperty("ratingScore")
  private int ratingScore;

  @JsonProperty("artists")
  private MutableList<ArtistsItem> artists;

  @JsonProperty("webLinks")
  private MutableList<WebLinksItem> webLinks;

  @JsonProperty("id")
  private int id;

  @JsonProperty("thumbUrl")
  private String thumbUrl;

  @JsonProperty("defaultName")
  private String defaultName;

  @JsonProperty("lyrics")
  private MutableList<LyricsItem> lyrics;

  @JsonProperty("additionalNames")
  private String additionalNames;

  @JsonProperty("createDate")
  private String createDate;

  @JsonProperty("mergedTo")
  private int mergedTo;

  @JsonProperty("originalVersionId")
  private int originalVersionId;

  @JsonProperty("defaultNameLanguage")
  private String defaultNameLanguage;

  @JsonProperty("lengthSeconds")
  private int lengthSeconds;

  @JsonProperty("pvs")
  private MutableList<PvsItem> pvs;

  @JsonProperty("songType")
  private String songType;

  @JsonProperty("version")
  private int version;

  @JsonProperty("tags")
  private MutableList<TagsItem> tags;

  @JsonProperty("deleted")
  private boolean deleted;

  @JsonProperty("names")
  private MutableList<NamesItem> names;

  @JsonProperty("favoritedTimes")
  private int favoritedTimes;

  @JsonProperty("name")
  private String name;

  @JsonProperty("pvServices")
  private String pvServices;

  @JsonProperty("releaseEvent")
  private ReleaseEvent releaseEvent;

  @JsonProperty("status")
  private String status;

  @Override
  public String toString() {
    return
        "Song{" +
            "albums = '" + albums + '\'' +
            ",publishDate = '" + publishDate + '\'' +
            ",mainPicture = '" + mainPicture + '\'' +
            ",artistString = '" + artistString + '\'' +
            ",ratingScore = '" + ratingScore + '\'' +
            ",artists = '" + artists + '\'' +
            ",webLinks = '" + webLinks + '\'' +
            ",id = '" + id + '\'' +
            ",thumbUrl = '" + thumbUrl + '\'' +
            ",defaultName = '" + defaultName + '\'' +
            ",lyrics = '" + lyrics + '\'' +
            ",additionalNames = '" + additionalNames + '\'' +
            ",createDate = '" + createDate + '\'' +
            ",mergedTo = '" + mergedTo + '\'' +
            ",originalVersionId = '" + originalVersionId + '\'' +
            ",defaultNameLanguage = '" + defaultNameLanguage + '\'' +
            ",lengthSeconds = '" + lengthSeconds + '\'' +
            ",pvs = '" + pvs + '\'' +
            ",songType = '" + songType + '\'' +
            ",version = '" + version + '\'' +
            ",tags = '" + tags + '\'' +
            ",deleted = '" + deleted + '\'' +
            ",names = '" + names + '\'' +
            ",favoritedTimes = '" + favoritedTimes + '\'' +
            ",name = '" + name + '\'' +
            ",pvServices = '" + pvServices + '\'' +
            ",releaseEvent = '" + releaseEvent + '\'' +
            ",status = '" + status + '\'' +
            "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Song song = (Song) o;
    return id == song.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}