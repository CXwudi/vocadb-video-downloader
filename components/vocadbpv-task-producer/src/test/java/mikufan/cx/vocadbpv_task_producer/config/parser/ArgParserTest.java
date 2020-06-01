package mikufan.cx.vocadbpv_task_producer.config.parser;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ArgParserTest {
  private DefaultParser parser = new DefaultParser();

  /**
   * print and make sure help message looks correct
   */
  @Test @SneakyThrows
  void testCreateOptions() {
    var options = ArgParser.createOptions();
    var cmdLine = parser.parse(options, new String[]{"-help"});
    if (cmdLine.hasOption("-help")){
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("vocadbpv-task-producer", options);
      assertTrue(true);
    }
  }

  /**
   * should throw if invalid args
   */
  @Test @SneakyThrows
  void testInvalidOptions(){
    assertThrows(UnrecognizedOptionException.class, () -> {
      var options = ArgParser.createOptions();
      var cmdLine = parser.parse(options, new String[]{"-asd"});
    });
  }

  /**
   * should throw if missing argument in option
   */
  @Test @SneakyThrows
  void testEmptyArg(){
    assertThrows(MissingArgumentException.class, () -> {
      var options = ArgParser.createOptions();
      var cmdLine = parser.parse(options, new String[]{"-i"});
    });

  }

  @Test @SneakyThrows
  void testEmptyArgBeforeAnotherOption(){
    var options = ArgParser.createOptions();
    var cmdLine = parser.parse(options, new String[]{"-i -f"});
    if(cmdLine.hasOption("i")){
      var value = cmdLine.getOptionValue("i");
      //todo: finish this test
      assertTrue(true);
    }
    fail();
  }
}