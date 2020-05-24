package mikufan.cx.common.entity.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common.util.jackson.ObjMapperUtil;

import java.io.File;

@Slf4j
class PvTaskJsonSerializationTest {
  private ObjectMapper objectMapper = ObjMapperUtil.createDefault();

  private File testJsonFilesPath = new File("src/test/resources/task/");


}
