package mikufan.cx.vocadb_pv_downloader.config;

import mikufan.cx.vocadb_pv_downloader.config.entity.AppConfig;
import mikufan.cx.vocadb_pv_downloader.config.parser.OptionFactory;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;

/**
 * main facade class to get {@link AppConfig} from command lines
 * @author CX无敌
 */
public class ConfigFactory {

  public AppConfig getConfig(String[] args) throws VocaDbPvDlException{
    var options = new OptionFactory().createOptions();

    return AppConfig.builder().build();
  }
}
