package mikufan.cx.vocadb_pv_downloader.config.parser;

import org.apache.commons.cli.HelpFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OptionFactoryTest {
  private final OptionFactory optionFactory = new OptionFactory();;

  @Test
  void printHelpAndCheck() {
    var options = optionFactory.createOptions();
    HelpFormatter helpFormatter = new HelpFormatter();
    helpFormatter.printHelp("tested title", options);
    assertTrue(true);
  }
}