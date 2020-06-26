package mikufan.cx.vocadb_pv_task_producer.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.vocadb.api.songList.get_listid_songs.PartialSongList;
import mikufan.cx.common_vocaloid_util.jackson.JsonMapperUtil;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * the stateless class that contains the real implementation for reading json response from VocaDB <br/>
 * endpoint: GET https://vocadb.net/api/songLists/{listId}/songs
 *
 * @author CX无敌
 */
@Slf4j
@RequiredArgsConstructor
public class ListFetcher {

  private final int maxResult;

  private static final ObjectMapper mapper = JsonMapperUtil.createDefaultForReadOnly();
  private static final BasicHttpClientResponseHandler responseHandler = new BasicHttpClientResponseHandler();

  /**
   * get a fully concattedList response of the endpoint using
   *
   * @param listId the id of song list
   * @param httpClient the http client to used for getting songs list
   */
  public PartialSongList getVocadbListOfId(int listId, CloseableHttpClient httpClient) throws VocaDbPvTaskException {
    PartialSongList concattedList = null;
    int start = 0;
    boolean hasMore;
    // sequentially fetching songs until done
    do {
      log.debug("start fetching maximum {} songs from index {}", start + maxResult, start + 1);
      PartialSongList partialList = partialListOfIdOfIndex(httpClient, listId, start);
      if (partialList.getItems().isEmpty()) {
        log.info("songLists {} has empty collection, returning now", listId);
        return partialList;
      }

      log.debug("concating the partial result into the main result list");
      concattedList = concatTwoLists(concattedList, partialList);

      log.debug("checking if more songs to fetch");
      int lastIndex = concattedList.getItems().getLastOptional()
          .orElseThrow(() -> new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_302, "Invalid empty collection"))
          .getOrder();
      int totalCount = concattedList.getTotalCount();
      if (lastIndex != totalCount) {
        log.debug("lastIndex = {}, totalCount = {}, more songs need to be fetched, continuing..", lastIndex, totalCount);
        hasMore = true;
        start = lastIndex;
      } else {
        log.debug("lastIndex = {}, totalCount = {}, all songs fetched", lastIndex, totalCount);
        hasMore = false;
      }
    } while (hasMore);
    checkIsCompletedAndSorted(concattedList);
    log.info("finished retrieving all {} songs from VocaDB song list of id {}", concattedList.getItems().size(), listId);
    return concattedList;
  }

  @SneakyThrows
  protected PartialSongList partialListOfIdOfIndex(CloseableHttpClient httpClient, int listId, int start) {
    try {
      var uri = getUri(listId, start);
      var getRequest = new HttpGet(uri);
      getRequest.setHeaders(new BasicHeader("Accept", "application/json"));

      var json = httpClient.execute(getRequest, responseHandler);
      return mapper.readValue(json, PartialSongList.class);
    } catch (URISyntaxException e){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_303, "Incorrect URI", e);
    } catch (IOException e) {
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_304,
          String.format("Failed to fetch songs from %s of list id %s", start, listId), e);
    }
  }

  protected URI getUri(int listId, int start) throws URISyntaxException {
    return new URIBuilder()
        .setScheme("https")
        .setHost("vocadb.net")
        .setPathSegments("api", "songLists", String.valueOf(listId), "songs")
        .setParameters(
            new BasicNameValuePair("start", String.valueOf(start)),
            new BasicNameValuePair("maxResults", String.valueOf(maxResult)),
            new BasicNameValuePair("getTotalCount", String.valueOf(true)),
            new BasicNameValuePair("fields", "PVs"))
        .build();
  }

  /**
   * concate two {@link PartialSongList} into one <p>
   * if concattedList is null, it will be simply replaced by parialList
   *
   * @param concattedList to
   * @param partialList   from
   * @return concattedList list
   */
  private PartialSongList concatTwoLists(PartialSongList concattedList, @NonNull PartialSongList partialList) {
    if (concattedList == null) {
      return partialList;
    } else {
      concattedList.getItems().addAll(partialList.getItems());
      return concattedList;
    }
  }

  /**
   * log warm message is the list is not completed or is not sorted
   *
   * @param concattedList list to check
   */
  private void checkIsCompletedAndSorted(PartialSongList concattedList) {
    var size = concattedList.getItems().size();
    var totalCount = concattedList.getTotalCount();
    if (size != totalCount) {
      log.warn("the end result song list fails to contain all songs. Recommend to re-run this program again");
      log.warn("expected # of songs = {}, actual # of songs collected = {}", totalCount, size);
    }
    var isSorted = concattedList.getItems().zipWithIndex().allSatisfy(pair -> pair.getOne().getOrder() - 1 == pair.getTwo());
    if (!isSorted) {
      log.warn("the end result song list is not in order. Probably a bug in this program");
    }
  }
}
