package mikufan.cx.common_entity.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mikufan.cx.common_entity.pv.util.SamplePvsGenerator;
import mikufan.cx.common_util.jackson.ObjMapperUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class VocaDbPvTaskJsonSerializationTest {
  private ObjectMapper mapper = ObjMapperUtil.createDefaultJsonMapper();

  private File parent = new File("src/test/resources/task/");

  /**
   * should able to parse json model
   */
  @Test
  @SneakyThrows
  void testParseJsonModel() {
    val task = mapper.readValue(new File(parent, "pvTaskModel.json"), VocaDbPvTask.class);
    log.info("task = {}", task);
    assertTrue(true);
  }

  @Test
  @SneakyThrows
  void testWrite() {
    var pvs = SamplePvsGenerator.generateVocadbPvs();
    var task = new VocaDbPvTask("2020 Song Task Test");
    //put half in tudo, put half in done
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
