package mikufan.cx.vocadb_pv_task_producer.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mikufan.cx.common_entity.vocadb.ResponseSongList;
import mikufan.cx.common_util.jackson.JsonMapperUtil;

/**
 * a wrapper class of jackson parsing json for {@link ResponseSongList}, used by {@link ListFetcher} only
 * @author CX无敌
 */
public class ResponseListParser {
  private final ObjectMapper mapper = JsonMapperUtil.createDefaultForReadOnly();

  public ResponseSongList toObj(String json) throws JsonProcessingException {
    return mapper.readValue(json, ResponseSongList.class);
  }
}
