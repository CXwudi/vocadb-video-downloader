package mikufan.cx.vocadb_pv_task_producer.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.project_vd_common_util.jackson.JsonMapperUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class ListFetcherTest  extends WithHttpClientSupport {
  private final int testedMaxResult = 5;
  private final ListFetcher fetcher = new ListFetcher(testedMaxResult);


  @Test @SneakyThrows
  void shouldGetUriOfIdFromAnIndex() {
    var uri = fetcher.getUri(8738, 15);
    assertEquals(
        "https://vocadb.net/api/songLists/8738/songs?start=15&maxResults=5&getTotalCount=true&fields=PVs%2CAlbums", //%2CArtists
        uri.toString()
    );
  }

  @Disabled // disabled to avoid high network volume on VocaDB caused by github CI
  @SneakyThrows
  void shouldGetPartialList() {
    var result = fetcher.partialListOfIdOfIndex(testedHttpClient, 8738, 0);
    var objMapperForDebug = JsonMapperUtil.createDefault();
    log.info("result = \n{}", objMapperForDebug.writeValueAsString(result));
    assertEquals(testedMaxResult, result.getItems().size());
    assertEquals(testedMaxResult, result.getItems().getLastOptional().get().getOrder());
  }

  @Disabled
  @SneakyThrows
  void getVocadbListOfId() {
    var max = 50;
    ListFetcher fetcher = new ListFetcher(max);
    var result = fetcher.getVocadbListOfId(8738, testedHttpClient);
    var objMapperForDebug = JsonMapperUtil.createDefault();
    log.info("result = \n{}", objMapperForDebug.writeValueAsString(result));
    assertEquals(result.getTotalCount(), result.getItems().size());
    assertEquals(result.getTotalCount(), result.getItems().getLastOptional().get().getOrder());
  }

}