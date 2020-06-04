package mikufan.cx.vocadbpv_task_producer.config.parser;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadbpv_task_producer.util.exception.VocaDbPvTaskException;
import org.apache.commons.cli.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ArgParserTest {
  private final DefaultParser parser = new DefaultParser();
  private final Options options = ArgParser.PARSER.createOptions();

  /**
   * print and make sure help message looks correct
   */
  @Test
  @SneakyThrows
  void testCreateOptions() {
    var cmdLine = parser.parse(options, new String[]{"-help"});
    if (cmdLine.hasOption("help")) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("vocadbpv-task-producer", options);
      assertTrue(true);
    } else {
      fail();
    }
  }

  /**
   * should throw if invalid option
   */
  @Test
  @SneakyThrows
  void testInvalidOptions() {
    assertThrows(UnrecognizedOptionException.class, () -> parser.parse(options, new String[]{"-asd"}));
    assertThrows(VocaDbPvTaskException.class, () -> ArgParser.parse(new String[]{"-asd"}));
  }

  /**
   * should throw if missing argument in option
   */
  @Test
  @SneakyThrows
  void testEmptyArgInOpt() {
    assertThrows(MissingArgumentException.class, () -> parser.parse(options, new String[]{"-i"}));
    assertThrows(MissingArgumentException.class, () -> parser.parse(options, new String[]{"--list-id"}));
    assertThrows(VocaDbPvTaskException.class, () -> ArgParser.parse(new String[]{"-i"}));

  }

  /**
   * same test but just put another option after -i
   */
  @Test
  @SneakyThrows
  void testEmptyArgInOpt2() {
    assertThrows(MissingArgumentException.class, () -> parser.parse(options, new String[]{"-i", "-f"}));
    assertThrows(VocaDbPvTaskException.class, () -> ArgParser.parse(new String[]{"-i", "-f"}));
  }

  /**
   * should print help if no arg is provided
   */
  @Test
  void testEmptyArg() {
    assertThrows(VocaDbPvTaskException.class, () -> ArgParser.parse(new String[]{}));
    assertThrows(VocaDbPvTaskException.class, () -> ArgParser.parse(new String[]{""}));
  }


}