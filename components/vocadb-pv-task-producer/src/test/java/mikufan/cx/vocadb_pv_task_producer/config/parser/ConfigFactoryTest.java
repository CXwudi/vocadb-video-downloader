package mikufan.cx.vocadb_pv_task_producer.config.parser;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
class ConfigFactoryTest {

  @Test
  void testHelp(){
    shouldThrowVocaDbExpByParser(VocaDbPvTaskRCI.MIKU_TASK_001, "-help");
    shouldThrowVocaDbExpByParser(VocaDbPvTaskRCI.MIKU_TASK_009, "-i", "1234", "-o", "fakeDir", "-s", "fakeSetting", "-help");
  }

  /**
   * should only success if it is a dir
   */
  @Test
  void testParsingOutputDir(){
    shouldThrowVocaDbExpByParser(VocaDbPvTaskRCI.MIKU_TASK_004, "-i", "1234", "-o", "pom.xml", "-s", "fakeSetting");
    // missing an required argument in option in cmd will be blocked during the actual parsing phase,
    // so the error code is 001
    shouldThrowVocaDbExpByParser(VocaDbPvTaskRCI.MIKU_TASK_001, "-i", "1234", "-o", "-s", "fakeSetting");

    var config = ConfigFactory.parse(new String[]{"-i", "1234", "-o", "src", "-s", "src/test/resources/sample-config.yaml"});
    assertEquals(Paths.get("src"), config.getOutputDir());
  }

  private void shouldThrowVocaDbExpByParser(VocaDbPvTaskRCI rci, String... cmdLine){
    try {
      ConfigFactory.parse(cmdLine);
      fail();
    } catch (Exception e){ // parse method used sneakThrow, so we can't catch a specific exception
      if (e instanceof VocaDbPvTaskException ){
        VocaDbPvTaskException exp = (VocaDbPvTaskException) e;
        assertEquals(rci, exp.getRci());
      } else {
        fail(e);
      }

    }
  }

}