package mikufan.cx.vocadb_pv_task_producer.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.vocadb.api.songList.get_listid_songs.PartialSongList;
import mikufan.cx.common_vocaloid_entity.vocadb.api.songList.get_listid_songs.SongInListForApiContract;
import mikufan.cx.common_vocaloid_entity.vocadb.models.ArtistForSongContract;
import mikufan.cx.common_vocaloid_entity.vocadb.models.SongForApiContract;
import mikufan.cx.common_vocaloid_util.exception.ThrowableConsumer;
import mikufan.cx.common_vocaloid_util.jackson.JsonMapperUtil;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.Pair;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The stateless class to fix any song with an artist string with any of "various" or "unknown". <p>
 * This requires an endpoint GET https://vocadb.net/api/songs/246033
 * @author CX无敌
 */
@Slf4j
@RequiredArgsConstructor
public class ArtistFieldFixer {

  public static final String VARIOUS = "Various";
  public static final String UNKNOWN = "Unknown";

  private static final ObjectMapper mapper = JsonMapperUtil.createDefaultForReadOnly();
  private static final BasicHttpClientResponseHandler responseHandler = new BasicHttpClientResponseHandler();

  public void tryFixAll(@NonNull PartialSongList songList, CloseableHttpClient httpClient){
    var songToFixIndexList = songList.getItems().zipWithIndex()
        .select(pair -> containAmbiguousStrings(pair.getOne().getSong().getArtistString())).collect(Pair::getTwo);
    songToFixIndexList.forEach(ThrowableConsumer.toConsumer(i -> {
      var songInListToFix = songList.getItems().get(i);
      log.debug("fixing {}th song '{}' with artist name '{}'",
          i, songInListToFix.getSong().getName(),
          songInListToFix.getSong().getArtistString());
      var fixed = SongInListForApiContract.builder()
          .notes(songInListToFix.getNotes())
          .order(songInListToFix.getOrder())
          .song(fixArtist(songInListToFix.getSong(), httpClient))
          .build();
      songList.getItems().set(i, fixed);
    }));
  }

  public boolean containAmbiguousStrings(String artistStr){
    return containsWithoutCase(artistStr, VARIOUS) || containsWithoutCase(artistStr, UNKNOWN);
  }

  protected boolean containsWithoutCase(String str1, String str2) {
    return str1.toLowerCase().contains(str2.toLowerCase());
  }

  /**
   * main impl of replacing "various" in artist field with more precised information.
   * also should embed the new gotten Artists json info into the song
   * @param song the song to be fixed
   * @param httpClient the http client to be used for
   * @return new song containing fixed artist, detail artists, and PVs info
   */
  public SongForApiContract fixArtist(SongForApiContract song, CloseableHttpClient httpClient) throws VocaDbPvTaskException {
    try {
      var artistStr = song.getArtistString();
      MutableList<ArtistForSongContract> artists = Lists.mutable.empty();
      // fix various
      if (containsWithoutCase(artistStr, VARIOUS) ){
        artists.addAll(getArtists(song.getId(), httpClient));
        artistStr = formArtistInfo(artists);
        log.debug("artist str with 'various', '{}' is now replaced by = '{}'", song.getArtistString(), artistStr);
      }

      // fix unknown
      if (containsWithoutCase(artistStr, UNKNOWN)){
        artistStr = removeUnknown(artistStr);
        log.debug("artist str with 'unknown', '{}' is now replaced by '{}'", song.getArtistString(), artistStr);
      }

      return combineNewInfo(song, artistStr, artists);
    } catch (URISyntaxException e){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_305, "Incorrect URI", e);
    } catch (IOException e) {
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_306,
          String.format("Failed to fix artist string for song id=%s, name=%s", song.getId(), song.getName()), e);
    }
  }


  /**
   * real imple of using http client to call a get request on
   */
  protected MutableList<ArtistForSongContract> getArtists(int id, CloseableHttpClient httpClient) throws URISyntaxException, IOException {
    var uri = new URIBuilder()
        .setScheme("https")
        .setHost("vocadb.net")
        .setPathSegments("api", "songs", String.valueOf(id))
        .setParameters(
            new BasicNameValuePair("fields", "Artists"))
        .build();
    var getRequest = new HttpGet(uri);
    getRequest.setHeaders(new BasicHeader("Accept", "application/json"));

    var json = httpClient.execute(getRequest, responseHandler);
    var detailedSong = mapper.readValue(json, SongForApiContract.class);
    return detailedSong.getArtists();
  }

  /**
   * @param artists input list of artists
   * @return a string in forms of "producers feat. vocalist"
   */
  protected String formArtistInfo(MutableList<ArtistForSongContract> artists) {
    var vocalist = artists.select(artist -> artist.getCategories().equals("Vocalist"))
        .collect(ArtistForSongContract::getName);
    var performers = artists.select(artist -> artist.getCategories().equals("Producer"))
        .collect(ArtistForSongContract::getName);
    return performers.makeString(", ") + " feat. " + vocalist.makeString(", ");
  }

  protected String removeUnknown(String artistStr) {
    return artistStr
        .replace(UNKNOWN.toLowerCase(), "")
        .replace(UNKNOWN.toUpperCase(), "")
        .replace(UNKNOWN, "")
        .replace(" ()", "")
        .replace("()", "");
  }


  protected SongForApiContract combineNewInfo(SongForApiContract song, String artistStr, MutableList<ArtistForSongContract> artists) {
    // I heat this part so much, good that intellij has the alt + left-click functions so useful
    return SongForApiContract.builder()
        .additionalNames(song.getAdditionalNames())
        .albums(song.getAlbums())
        .artists(artists)
        .artistString(artistStr)
        .createDate(song.getCreateDate())
        .defaultName(song.getDefaultName())
        .defaultNameLanguage(song.getDefaultNameLanguage())
        .deleted(song.isDeleted())
        .favoritedTimes(song.getFavoritedTimes())
        .id(song.getId())
        .lengthSeconds(song.getLengthSeconds())
        .lyrics(song.getLyrics())
        .mainPicture(song.getMainPicture())
        .mergedTo(song.getMergedTo())
        .name(song.getName())
        .names(song.getNames())
        .originalVersionId(song.getOriginalVersionId())
        .publishDate(song.getPublishDate())
        .pvs(song.getPvs())
        .pvServices(song.getPvServices())
        .ratingScore(song.getRatingScore())
        .releaseEvent(song.getReleaseEvent())
        .songType(song.getSongType())
        .status(song.getStatus())
        .tags(song.getTags())
        .thumbUrl(song.getThumbUrl())
        .version(song.getVersion())
        .webLinks(song.getWebLinks())
        .build();
  }

}
