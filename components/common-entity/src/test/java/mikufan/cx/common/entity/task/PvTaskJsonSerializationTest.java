package mikufan.cx.common.entity.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mikufan.cx.common.util.jackson.ObjMapperUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

import static mikufan.cx.common.entity.pv.util.SamplePvsGenerator.generateVocadbPvs;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class PvTaskJsonSerializationTest {
  private ObjectMapper mapper = ObjMapperUtil.createDefault();

  private File parent = new File("src/test/resources/task/");

  /**
   * should able to parse json model
   */
  @Test
  @SneakyThrows
  void testParseJsonModel() {
    val task = mapper.readValue(new File(parent, "pvTaskModel.json"), PvTask.class);
    log.info("task = {}", task);
    assertTrue(true);
  }

  @Test
  @SneakyThrows
  void testWrite() {
    var pvs = generateVocadbPvs();
    var task = new PvTask("2020 Song Task Test");
    pvs.zipWithIndex().forEachWith((pvIntegerPair, taskk) -> {
      var pv = pvIntegerPair.getOne();
      var index = pvIntegerPair.getTwo();
      if (index % 2 == 0) {
        taskk.markDone(pv);
      } else {
        taskk.markTodo(pv);
      }
    }, task);
    var json = mapper.writeValueAsString(task);
    log.debug("task = \n{}", json);


    var outputFile = new File(parent, "testOutput.json");
    outputFile.deleteOnExit();
    mapper.writeValue(outputFile, task);

    assertTrue(outputFile.exists());
  }

}
