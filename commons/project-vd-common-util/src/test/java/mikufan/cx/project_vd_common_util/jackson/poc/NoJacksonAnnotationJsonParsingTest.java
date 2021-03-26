package mikufan.cx.project_vd_common_util.jackson.poc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.project_vd_common_util.jackson.JsonMapperUtil;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * there are 4 ways to make it works: https://stackoverflow.com/a/51465038/8529009
 *
 * We prefer the 1st way, declare no arg constructor and getters, works for final fields as well
 */
@Slf4j
class NoJacksonAnnotationJsonParsingTest {
  private final ObjectMapper mapper = JsonMapperUtil.createDefault();
  private final Path baseDir = Paths.get("src/test/resources/json");

  @Test @SneakyThrows
  void testReadFromJson1(){
    var json = baseDir.resolve("dummy1.json");
    var dummy = mapper.readValue(json.toFile(), Dummy.class);
    log.info("dummy = {}", dummy);
    assertTrue(true);
  }

  @Test @SneakyThrows
  void testWriteToJson1(){
    var dummy = new Dummy("yeah", 15, "nice");
    var json = mapper.writeValueAsString(dummy);
    log.info("json = {}", json);
    assertTrue(true);
  }

  @Test @SneakyThrows
  void testReadFromJson2(){
    var json = baseDir.resolve("dummy2.json");
    var dummyHolder = mapper.readValue(json.toFile(), DummyHolder.class);
    log.info("dummyHolder = {}", dummyHolder);
    assertTrue(true);
  }

  @Test @SneakyThrows
  void testWriteToJson2(){
    var dummy = new Dummy("yeah yeah", 51, "nice dummy");
    var dummyHolder = new DummyHolder(dummy, "is just a dummy");
    var json = mapper.writeValueAsString(dummyHolder);
    log.info("json = {}", json);
    assertTrue(true);
  }

  @Test @SneakyThrows
  void testReadFromJson3(){
    var json = baseDir.resolve("dummy3.json");
    var dummyIndependent = mapper.readValue(json.toFile(), DummyIndependent.class);
    log.info("dummyIndependent = {}", dummyIndependent);
    assertTrue(true);
  }

  @Test @SneakyThrows
  void testWriteToJson3(){
    var dummy = new DummyIndependent(21, "nice format", true, false, 5, 2020);
    var json = mapper.writeValueAsString(dummy);
    log.info("json = {}", json);
    assertTrue(true);
  }

  @Test @SneakyThrows
  void testReadFromJson4(){
    var json = baseDir.resolve("dummy4.json");
    var singleFieldDummy = mapper.readValue(json.toFile(), SingleFieldDummy.class);
    log.info("singleFieldDummy = {}", singleFieldDummy);
    assertTrue(true);
  }

  @Test @SneakyThrows
  void testWriteToJson4(){
    var dummy = new SingleFieldDummy("oh yeah");
    var json = mapper.writeValueAsString(dummy);
    log.info("json = {}", json);
    assertTrue(true);
  }
}