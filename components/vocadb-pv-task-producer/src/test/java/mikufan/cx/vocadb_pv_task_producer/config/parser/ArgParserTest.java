package mikufan.cx.vocadb_pv_task_producer.config.parser;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.pv.service.PvServiceStr;
import mikufan.cx.project_vd_common_util.pv_service.SupportedPvServices;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class ArgParserTest {
  //construct options
  private final Options options = new OptionsFactory().createOptions();
  //real parsing
  private final ArgParser parser = new ArgParser();

  @Test @SneakyThrows
  void testGetTaskFile(){
    var cmdLine = parser.parseArgs(new String[]{"-i", "1234", "-f", "myTask.json"}, options);
    var path = parser.getTaskJsonOrDefault(cmdLine, parser.getListIdOrThrow(cmdLine, options));
    log.info("path = {}", path);
    assertEquals(Path.of(".", "myTask.json").toAbsolutePath().normalize(), path.toAbsolutePath());
  }

  @Test @SneakyThrows
  void testThrowOnDirInGetTaskFile(){
    var path = Files.createDirectory(Path.of("myTempDir"));
    path.toFile().deleteOnExit();
    var cmdLine = parser.parseArgs(new String[]{"-i", "1234", "-f", path.getFileName().toString()}, options);
    assertThrows(VocaDbPvTaskException.class, () -> parser.getTaskJsonOrDefault(cmdLine, parser.getListIdOrThrow(cmdLine, options)));
  }

  /**
   * can give the preference of pv website, and complete it
   */
  @Test @SneakyThrows
  void testGetPvPref() {
    var cmdLine = parser.parseArgs(new String[]{"-i", "1234",
        "-p", String.format("%s,%s", PvServiceStr.YOUTUBE, PvServiceStr.NICONICO)},
        options);
    var pvPref = parser.getPvPrefOrDefault(cmdLine);
    log.debug("pvPref = {}", pvPref);
    assertEquals(SupportedPvServices.getSupportedPvServices().size(), pvPref.size());
  }

  /**
   * can throw when invalid website detected
   */
  @Test @SneakyThrows
  void testThrowInPvPref() {
    var cmdLine = parser.parseArgs(new String[]{"-i", "1234",
            "-p", String.format("%s,%s,%s", PvServiceStr.NICONICO, PvServiceStr.YOUTUBE, "InvalidWebsite")},
        options);
    assertThrows(VocaDbPvTaskException.class, () -> parser.getPvPrefOrDefault(cmdLine));
  }
}