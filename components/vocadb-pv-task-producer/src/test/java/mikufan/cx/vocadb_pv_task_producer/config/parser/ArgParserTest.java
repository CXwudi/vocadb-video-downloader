package mikufan.cx.vocadb_pv_task_producer.config.parser;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_entity.pv.PvService;
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
    var path = parser.getTaskJson(cmdLine, parser.getListIdOrThrow(cmdLine, options));
    assertEquals(Path.of(".", "myTask.json").toAbsolutePath().normalize(), path.toAbsolutePath());
  }

  @Test @SneakyThrows
  void testThrowOnDirInGetTaskFile(){
    var path = Files.createDirectory(Path.of("myTempDir"));
    path.toFile().deleteOnExit();
    var cmdLine = parser.parseArgs(new String[]{"-i", "1234", "-f", path.getFileName().toString()}, options);
    assertThrows(VocaDbPvTaskException.class, () -> parser.getTaskJson(cmdLine, parser.getListIdOrThrow(cmdLine, options)));
  }

  /**
   * can give the preference of pv website, and complete it
   */
  @Test @SneakyThrows
  void testGetPvPref() {
    var cmdLine = parser.parseArgs(new String[]{"-i", "1234",
        "-p", String.format("%s,%s", PvService.NICONICO.getServiceName(), PvService.YOUTUBE.getServiceName())},
        options);
    var args = parser.getPvPref(cmdLine);
    assertEquals(PvService.values().length, args.size());
  }

  /**
   * can throw when invalid website detected
   */
  @Test @SneakyThrows
  void testThrowInPvPref() {
    var cmdLine = parser.parseArgs(new String[]{"-i", "1234",
            "-p", String.format("%s,%s,%s", PvService.NICONICO.getServiceName(), PvService.YOUTUBE.getServiceName(), "InvalidWebsite")},
        options);
    assertThrows(VocaDbPvTaskException.class, () -> parser.getPvPref(cmdLine));
  }
}