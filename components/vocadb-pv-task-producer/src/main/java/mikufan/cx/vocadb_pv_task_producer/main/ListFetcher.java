package mikufan.cx.vocadb_pv_task_producer.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.vocadb.ResponseSongList;
import mikufan.cx.common_vocaloid_util.jackson.JsonMapperUtil;

/**
 * the stateless class that contains the real implementation for reading json response from VocaDB <br/>
 * endpoint: GET https://vocadb.net/api/songLists/{listId}/songs
 * @author CX无敌
 */
@Slf4j @RequiredArgsConstructor
public class ListFetcher {
  private final ObjectMapper mapper = JsonMapperUtil.createDefaultForReadOnly();
  private final int maxResult;

  public ResponseSongList getVocadbListOfId(int listId, String ua){
    return null;
  }

  public ResponseSongList partialListOfIdOfIndex(int listId, String ua, int start){
    return null;
  }
}
