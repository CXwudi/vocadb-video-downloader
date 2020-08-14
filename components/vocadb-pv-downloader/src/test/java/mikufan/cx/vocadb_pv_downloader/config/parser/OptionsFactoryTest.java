package mikufan.cx.vocadb_pv_downloader.config.parser;

import org.apache.commons.cli.HelpFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OptionsFactoryTest {
  private final OptionsFactory optionsFactory = new OptionsFactory();;

  @Test
  void printHelpAndCheck() {
    var options = optionsFactory.createOptions();
    HelpFormatter helpFormatter = new HelpFormatter();
    helpFormatter.printHelp("tested title", options);
    assertTrue(true);
  }
}