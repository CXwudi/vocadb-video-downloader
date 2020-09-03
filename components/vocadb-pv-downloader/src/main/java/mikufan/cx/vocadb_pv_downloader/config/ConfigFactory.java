package mikufan.cx.vocadb_pv_downloader.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_downloader.config.entity.AppConfig;
import mikufan.cx.vocadb_pv_downloader.config.parser.ArgParser;
import mikufan.cx.vocadb_pv_downloader.config.parser.OptionsFactory;
import mikufan.cx.vocadb_pv_downloader.config.validator.UserConfigValidator;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;
import org.apache.commons.cli.CommandLine;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * main facade class to get {@link AppConfig} from command lines
 * @author CX无敌
 */
@Slf4j @Configuration @Lazy
@RequiredArgsConstructor
public class ConfigFactory {
  private final OptionsFactory optionsFactory;
  private final ArgParser parser;
  private final UserConfigValidator configValidator;

  @Bean
  public AppConfig getConfig(@NonNull ApplicationArguments args) throws VocaDbPvDlException{
    //construct options
    var options = optionsFactory.createOptions();

    // real parsing, which also print help if any required options are missing
    log.debug("parsing commands");
    CommandLine cmdLine = parser.parseArgs(args.getSourceArgs(), options);
    // in case if all required args exist and user still want to print help only
    parser.checkAndPrintHelpAndQuitIfNeed(options, cmdLine);

    log.debug("setting up app config");
    var configBuilder = AppConfig.builder();
    configBuilder.inputDir(parser.getInputDirOrThrow(cmdLine));
    configBuilder.outputDir(parser.getOutputDirOrThrow(cmdLine));

    var userConfig = parser.getUserConfigOrThrow(cmdLine);
    configValidator.validate(userConfig);
    configBuilder.userConfig(userConfig);

    return configBuilder.build();
  }
}
