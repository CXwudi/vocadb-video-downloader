package mikufan.cx.vocadb_pv_task_producer.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.vocadb.ResponseSongList;
import mikufan.cx.common_vocaloid_util.jackson.JsonMapperUtil;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * the stateless class that contains the real implementation for reading json response from VocaDB <br/>
 * endpoint: GET https://vocadb.net/api/songLists/{listId}/songs
 * @author CX无敌
 */
@Slf4j @RequiredArgsConstructor
public class ListFetcher {
  private final ObjectMapper mapper = JsonMapperUtil.createDefaultForReadOnly();
  private final int maxResult;
  private final BasicHttpClientResponseHandler responseHandler = new BasicHttpClientResponseHandler();

  /**
   * get a fully concattedList response of the endpoint using
   * @param listId the id of song list
   * @param ua user-agent
   */
  public ResponseSongList getVocadbListOfId(int listId, String ua) throws VocaDbPvTaskException {
    log.info("creating HttpClient for retrieving VocaDB song list of id {}", listId);
    ResponseSongList concattedList = null;
    try(final var httpClient = HttpClients.custom()
        .setUserAgent(ua)
        .build()) {
      int start = 0;
      boolean hasMore;
      // sequentially fetching songs until done
      do {
        log.debug("start fetching maximum {} songs from index {}", start + maxResult, start + 1);
        ResponseSongList partialList = partialListOfIdOfIndex(httpClient, listId, ua, start);
        if (partialList.getItems().isEmpty()){
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
        if (lastIndex != totalCount){
          log.debug("lastIndex = {}, totalCount = {}, more songs to be fetched, continuing..", lastIndex, totalCount);
          hasMore = true;
          start = lastIndex;
        } else {
          log.debug("lastIndex = {}, totalCount = {}, all songs fetched", lastIndex, totalCount);
          hasMore = false;
        }
      } while (hasMore);
    } catch (IOException e){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_301, "Fail to handle HttpClient closable resource", e);
    }
    checkIsCompletedAndSorted(concattedList);
    log.info("finished retrieving VocaDB song list of id {}", listId);
    return concattedList;
  }

  @SneakyThrows
  protected ResponseSongList partialListOfIdOfIndex(CloseableHttpClient httpClient, int listId, String ua, int start) {
    try {
      var uri = getUri(listId, start);
      var getRequest = new HttpGet(uri);
      getRequest.setHeaders(new BasicHeader("Accept", "application/json"));

      var json = httpClient.execute(getRequest, responseHandler);
      return mapper.readValue(json,ResponseSongList.class);
    } catch (URISyntaxException | IOException e) {
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_302,
          String.format("Failed to fetch songs from %s of list id %s with user-agent %s", start, listId, ua), e);
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
   * concate two {@link ResponseSongList} into one <p>
   * if concattedList is null, it will be simply replaced by parialList
   * @param concattedList to
   * @param partialList from
   * @return concattedList list
   */
  private ResponseSongList concatTwoLists(ResponseSongList concattedList, @NonNull ResponseSongList partialList) {
    if (concattedList == null){
      return partialList;
    } else {
      concattedList.getItems().addAll(partialList.getItems());
      return concattedList;
    }
  }

  /**
   * log warm message is the list is not completed or is not sorted
   * @param concattedList list to check
   */
  private void checkIsCompletedAndSorted(ResponseSongList concattedList) {
    var size = concattedList.getItems().size();
    var totalCount = concattedList.getTotalCount();
    if (size != totalCount){
      log.warn("the end result song list fails to contain all songs. Recommend to re-run this program again");
      log.warn("expected # of songs = {}, actual # of songs collected = {}", totalCount, size);
    }
    var isSorted = concattedList.getItems().zipWithIndex().allSatisfy(pair -> pair.getOne().getOrder() - 1 == pair.getTwo());
    if (!isSorted){
      log.warn("the end result song list is not in order. Probably a bug in this program");
    }
  }
}
