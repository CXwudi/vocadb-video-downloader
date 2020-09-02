package mikufan.cx.vocadb_pv_task_producer.services.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import mikufan.cx.vocadb_pv_task_producer.services.config.entity.AppConfig;
import mikufan.cx.vocadb_pv_task_producer.services.config.entity.UserConfig;
import mikufan.cx.vocadb_pv_task_producer.services.config.parser.ArgParser;
import mikufan.cx.vocadb_pv_task_producer.services.config.parser.OptionsFactory;
import mikufan.cx.vocadb_pv_task_producer.services.config.validator.UserConfigValidator;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import org.apache.commons.cli.CommandLine;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * facade of parsing args from command line
 * @author CX无敌
 */
@Service @Lazy
@RequiredArgsConstructor
public class ConfigFactory {
  private final OptionsFactory optionsFactory;
  private final ArgParser parser;
  private final UserConfigValidator configValidator;

  @SneakyThrows(VocaDbPvTaskException.class)
  public AppConfig parse(String[] args){
    //construct options
    var options = optionsFactory.createOptions();
    // construct parser

    // real parsing, which also print help if any required options are missing
    CommandLine cmdLine = parser.parseArgs(args, options);
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
