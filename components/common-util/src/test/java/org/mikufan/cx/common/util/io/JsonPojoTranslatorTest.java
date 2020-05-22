package org.mikufan.cx.common.util.io;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class JsonPojoTranslatorTest {
  private JsonPojoTranslator<Dummy> dummyTranslator = new JsonPojoTranslator<>(JsonMapper.builder().build());
  private File dir = new File("src/test/resources");
  private File dummyJson = new File(dir, "dummy.json");
  private File missingFile = new File(dir, "missing.json");

  private Dummy dummyObj = new Dummy("CX", 22);

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
  @Test
  void testWrite(){
    var tempFile = new File(dir, "temp.json");
    tempFile.deleteOnExit();
    dummyTranslator.write(dummyObj, tempFile);
  }
}