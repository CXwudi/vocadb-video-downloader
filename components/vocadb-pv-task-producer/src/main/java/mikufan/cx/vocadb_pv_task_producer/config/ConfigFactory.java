package mikufan.cx.vocadb_pv_task_producer.config;

import lombok.RequiredArgsConstructor;
import mikufan.cx.vocadb_pv_task_producer.config.entity.AppConfig;
import mikufan.cx.vocadb_pv_task_producer.config.entity.UserConfig;
import mikufan.cx.vocadb_pv_task_producer.config.parser.ArgParser;
import mikufan.cx.vocadb_pv_task_producer.config.parser.OptionsFactory;
import mikufan.cx.vocadb_pv_task_producer.config.validator.UserConfigValidator;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import org.apache.commons.cli.CommandLine;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * a factory that lazily produce the singleton bean of {@link AppConfig}
 * @author CX无敌
 */
@Configuration
@Lazy
@RequiredArgsConstructor
public class ConfigFactory {
  private final OptionsFactory optionsFactory;
  private final ArgParser parser;
  private final UserConfigValidator configValidator;

  /**
   * produce the singleton bean of {@link AppConfig}
   * @param args application arguments from spring boot
   * @return the singleton bean of {@link AppConfig}
   */
  @Bean
  public AppConfig parse(ApplicationArguments args) throws VocaDbPvTaskException{
    //construct options
    var options = optionsFactory.createOptions();
    // construct parser

    // real parsing, which also print help if any required options are missing
    CommandLine cmdLine = parser.parseArgs(args.getSourceArgs(), options);
    // in case if all required args exist and user still want to print help only
    parser.checkAndPrintHelpAndQuitIfNeed(options, cmdLine);

    int listId = parser.getListIdOrThrow(cmdLine, options);
    var outputDir = parser.getOutputDirOrThrow(cmdLine);
    UserConfig userConfig = parser.getUserConfigOrThrow(cmdLine);

    //user config needs an external validator because it is parsed from Jackson
    configValidator.validate(userConfig);

    return AppConfig.builder()
        .listId(listId)
        .userConfig(userConfig)
        .outputDir(outputDir)
        .build();
  }
}
