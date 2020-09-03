package mikufan.cx.vocadb_pv_downloader.config.parser;

import mikufan.cx.project_vd_common_util.exception.ThrowableSupplier;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlRCI;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArgParserTest {
  private final Options options = new OptionsFactory().createOptions();
  private final ArgParser parser = new ArgParser();

  private <T> void assertThrowMyExp(ThrowableSupplier<T> supplier, VocaDbPvDlRCI rci){
    try {
      supplier.get();
    } catch (Exception e){
      assertTrue(e instanceof VocaDbPvDlException);
      var e1 = (VocaDbPvDlException)e;
      assertEquals(rci, e1.getRci());
    }
  }

  @Test
  void testNormalUsage() throws VocaDbPvDlException {
    var args = new String[]{"-i", "input", "-o", "output", "-s", "my-setting.yml"};
    parser.parseArgs(args, options);
    assertTrue(true);
  }

}