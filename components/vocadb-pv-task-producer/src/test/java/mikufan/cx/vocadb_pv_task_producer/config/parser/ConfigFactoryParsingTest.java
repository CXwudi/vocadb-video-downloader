package mikufan.cx.vocadb_pv_task_producer.config.parser;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_entity.pv.PvService;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import org.apache.commons.cli.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ConfigFactoryParsingTest {
  private final DefaultParser parser = new DefaultParser();
  private final Options options = new OptionsFactory().createOptions();

  /**
   * print and make sure help message looks correct
   */
  @Test
  @SneakyThrows
  void testCreateOptions() {
    var cmdLine = parser.parse(options, new String[]{"-help"});
    if (cmdLine.hasOption("help")) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("vocadb-pv-task-producer", options);
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
    assertThrows(VocaDbPvTaskException.class, () -> ConfigFactory.parse(new String[]{"-asd"}));
  }

  /**
   * should throw if missing argument for a declared option that requires arg. <br/>
   * noticed that if an option is not declared, it won't throw even though it requires arg
   */
  @Test
  @SneakyThrows
  void testThrowFromEmptyArgInOpt() {
    //put all options that requires arg to this array
    var opts = new OptionName[]{
        OptionName.LIST_ID, OptionName.REFERENCE_FILE, OptionName.TASK_FILE, OptionName.TASK_NAME,
        OptionName.PV_PREFERENCE, OptionName.USER_AGENT
    };
    for (var opt : opts){
      assertThrows(MissingArgumentException.class, () -> parser.parse(options, new String[]{"-" + opt.getOptName()}));
      assertThrows(MissingArgumentException.class, () -> parser.parse(options, new String[]{"-" + opt.getOptLongName()}));
      assertThrows(VocaDbPvTaskException.class, () -> ConfigFactory.parse(new String[]{"-" + opt.getOptName()}));
    }

  }

  /**
   * same test but just put another option after -i
   */
  @Test
  @SneakyThrows
  void testEmptyArgInOpt2() {
    assertThrows(MissingArgumentException.class, () -> parser.parse(options, new String[]{"-i", "-f", "file.txt"}));
    assertThrows(VocaDbPvTaskException.class, () -> ConfigFactory.parse(new String[]{"-i", "-f", "file.txt"}));
  }

  /**
   * should print help if no arg is provided
   */
  @Test
  void testEmptyArg() {
    assertThrows(VocaDbPvTaskException.class, () -> ConfigFactory.parse(new String[]{}));
    assertThrows(VocaDbPvTaskException.class, () -> ConfigFactory.parse(new String[]{""}));
  }

  /**
   * test if we can parse multiply arg in an options
   */
  @Test @SneakyThrows
  void testMultipleArgsInOption(){
    var cmdLine = parser.parse(options, new String[]{"-i", "1234",
        "-p", String.format("%s,%s", PvService.NICONICO.getServiceName(), PvService.YOUTUBE.getServiceName())});
    if (cmdLine.hasOption(OptionName.PV_PREFERENCE.getOptName())){
      var args = cmdLine.getOptionValues(OptionName.PV_PREFERENCE.getOptName());

      assertEquals(2, args.length);
    } else {
      fail();
    }

  }

}