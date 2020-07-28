package mikufan.cx.vocadb_pv_task_producer.config.parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import mikufan.cx.vocadb_pv_task_producer.config.entity.AppConfig;
import mikufan.cx.vocadb_pv_task_producer.config.validator.UserConfigValidator;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import org.apache.commons.cli.CommandLine;

/**
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigFactory {


  @SneakyThrows(VocaDbPvTaskException.class)
  public static AppConfig parse(String[] args){
    //construct options
    var options = new OptionsFactory().createOptions();
    // construct parser
    var parser = new ArgParser();

    // real parsing, which also print help if any required options are missing
    CommandLine cmdLine = parser.parseArgs(args, options);
    // in case if all required args exist and user still want to print help only
    parser.checkAndPrintHelpAndQuitIfNeed(options, cmdLine);

    var listId = parser.getListIdOrThrow(cmdLine, options);
    var outputDir = parser.getOutputDirOrThrow(cmdLine);
    var userConfig = parser.getUserConfigOrThrow(cmdLine);

    //user config needs an external validator because it is parsed from Jackson
    new UserConfigValidator().validate(userConfig);

    return AppConfig.builder()
        .listId(listId)
        .userConfig(userConfig)
        .outputDir(outputDir)
        .build();
  }
}
