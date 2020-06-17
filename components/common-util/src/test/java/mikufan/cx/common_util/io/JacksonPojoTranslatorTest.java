package mikufan.cx.common_util.io;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_util.jackson.YamlMapperUtil;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
class JacksonPojoTranslatorTest {
  private final JacksonPojoTranslator<Dummy> dummyTranslator = new JacksonPojoTranslator<>(JsonMapper.builder().build());
  private final Path dir = Path.of("src/test/resources/test");
  private final Path dummyJson = dir.resolve("dummy.json");
  private final Path missingFile = dir.resolve("missing.json");

  private final Dummy dummyObj = new Dummy("CX", 22);

  /**
   * should be able to read from json
   */
  @Test
  void testRead() {
    Optional<Dummy> dummy = dummyTranslator.read(dummyJson);
    assertTrue(dummy.isPresent());
    log.info("dummy = {}", dummy.get());
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
    var tempFile = dir.resolve("temp.json");
    tempFile.toFile().deleteOnExit();
    assertTrue(dummyTranslator.write(dummyObj, tempFile));
  }

  /**
   * should be able to overwrite the json file with an obj
   */
  @Test @SneakyThrows
  void testOverWrite(){
    var tempFile = dir.resolve("temp.json");
    if (Files.notExists(tempFile) && !tempFile.toFile().createNewFile()){ //make sure a file exists
      fail();
    }
    tempFile.toFile().deleteOnExit();
    assertTrue(dummyTranslator.write(dummyObj, tempFile));
  }

  @Test
  void testReadYaml(){
    var dummyYaml = dir.resolve("test/dummyYaml.yaml");
    var reader = new JacksonPojoTranslator<Dummy>(YamlMapperUtil.createDefaultForReadOnly());
    var yamlOpt = reader.read(dummyYaml);
    log.info("yamlOpt = {}", yamlOpt.get());
    assertTrue(true);
  }
}