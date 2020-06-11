package mikufan.cx.vocadb_pv_task_producer.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_entity.vocadb.ResponseSongList;

/**
 * the stateless class that contains the real implementation for reading json response from VocaDB <br/>
 * endpoint: GET https://vocadb.net/api/songLists/{listId}/songs
 * @author CX无敌
 */
@Slf4j @RequiredArgsConstructor
public class ListFetcher {
  private final ResponseListParser parser = new ResponseListParser();
  private final int maxResult;

  public ResponseSongList getVocadbListOfId(int listId, String ua){
    return null;
  }

  public ResponseSongList partialListOfIdOfIndex(int listId, String ua, int start){
    return null;
  }
}
