package mikufan.cx.common.entity.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mikufan.cx.common.entity.common.PvService;
import mikufan.cx.common.entity.pv.VocaDbPv;
import mikufan.cx.common.util.jackson.ObjMapperUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class VocaDbPvJsonSerializationTest {
  private final ObjectMapper mapper = ObjMapperUtil.createDefault();
  private final String parent = "src/test/resources/task";

  /**
   * should able to parse json model
   */
  @Test @SneakyThrows
  void readJsonModel(){
    val pv = mapper.readValue(new File(parent, "pvJsonModel.json"), VocaDbPv.class);
    assertEquals("", pv.getName());
    assertEquals("", pv.getPvId());
    assertEquals(PvService.NICONICO, pv.getService());
    assertEquals(0, pv.getSongId());
  }

  /**
   * should able to write json
   */
  @Test @SneakyThrows
  void writeJson(){
    val pv = new VocaDbPv("ERo-sPa1a5g", PvService.YOUTUBE,
        "八王子P × Giga「Gimme×Gimme feat. 初音ミク・鏡音リン」", 246033);
    val str = mapper.writeValueAsString(pv);
    log.info("str = {}", str);
    assertTrue(true);
  }

}