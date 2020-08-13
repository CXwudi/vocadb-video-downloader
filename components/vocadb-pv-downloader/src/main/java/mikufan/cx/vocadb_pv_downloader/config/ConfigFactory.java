package mikufan.cx.vocadb_pv_downloader.config;

import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_downloader.config.entity.AppConfig;
import mikufan.cx.vocadb_pv_downloader.config.parser.ArgParser;
import mikufan.cx.vocadb_pv_downloader.config.parser.OptionsFactory;
import mikufan.cx.vocadb_pv_downloader.config.validator.UserConfigValidator;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;
import org.apache.commons.cli.CommandLine;

/**
 * main facade class to get {@link AppConfig} from command lines
 * @author CX无敌
 */
@Slf4j
public class ConfigFactory {

  public AppConfig getConfig(String[] args) throws VocaDbPvDlException{
    //construct options
    var options = new OptionsFactory().createOptions();
    // construct parser
    var parser = new ArgParser();

    // real parsing, which also print help if any required options are missing
    log.debug("parsing commands");
    CommandLine cmdLine = parser.parseArgs(args, options);
    // in case if all required args exist and user still want to print help only
    parser.checkAndPrintHelpAndQuitIfNeed(options, cmdLine);

    log.debug("setting up user config");
    var configBuilder = AppConfig.builder();
    configBuilder.outputDir(parser.getOutputDirOrThrow(cmdLine));
    var userConfig = parser.getUserConfigOrThrow(cmdLine);
    new UserConfigValidator().validateAndFill(userConfig);
    configBuilder.userConfig(userConfig);

    return configBuilder.build();
  }
}
