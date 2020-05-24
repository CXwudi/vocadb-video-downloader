package mikufan.cx.common.util.io;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
class JsonPojoTranslatorTest {
  private final JsonPojoTranslator<Dummy> dummyTranslator = new JsonPojoTranslator<>(JsonMapper.builder().build());
  private final File dir = new File("src/test/resources");
  private final File dummyJson = new File(dir, "dummy.json");
  private final File missingFile = new File(dir, "missing.json");

  private final Dummy dummyObj = new Dummy("CX", 22);

  /**
   * should be able to read from json
   */
  @Test
  void testRead() {
    Optional<Dummy> dummy = dummyTranslator.read(dummyJson);
    assertTrue(dummy.isPresent());
  }

  /**
   * should not break if IOException happens
   */
  @Test
  void testReadMissingFile() {
    Optional<Dummy> dummy = dummyTranslator.read(missingFile);
    assertTrue(dummy.isEmpty());
  }

  /**
   * should be able to write an obj to json file
   */
  @Test @SneakyThrows
  void testWrite(){
    var tempFile = new File(dir, "temp.json");
    tempFile.deleteOnExit();
    assertTrue(dummyTranslator.write(dummyObj, tempFile));
  }

  /**
   * should be able to overwrite the json file with an obj
   */
  @Test @SneakyThrows
  void testOverWrite(){
    var tempFile = new File(dir, "temp.json");
    if (Files.notExists(tempFile.toPath()) && !tempFile.createNewFile()){ //make sure a file exists
      fail();
    }
    tempFile.deleteOnExit();
    assertTrue(dummyTranslator.write(dummyObj, tempFile));
  }
}