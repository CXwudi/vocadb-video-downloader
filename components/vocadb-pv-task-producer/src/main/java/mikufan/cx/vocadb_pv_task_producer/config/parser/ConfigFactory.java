package mikufan.cx.vocadb_pv_task_producer.config.parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_task_producer.config.entity.AppConfig;
import mikufan.cx.vocadb_pv_task_producer.config.entity.UserConfig;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import org.apache.commons.cli.CommandLine;

/**
 * @author CX无敌
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigFactory {


  @SneakyThrows(VocaDbPvTaskException.class)
  public static AppConfig parse(String[] args){
    //construct options
    var options = new OptionsFactory().createOptions();
    //real parsing
    var parser = new ArgParser();
    CommandLine cmdLine = parser.parseArgs(args, options);
    //if just need to print help
    parser.checkAndPrintHelpAndQuitIfNeed(options, cmdLine);

    var builder = UserConfig.builder();
    var listId = parser.getListIdOrThrow(cmdLine, options);
    builder.listId(listId);
    builder.userAgent(parser.getUserAgent(cmdLine));
    builder.taskName(parser.getTaskName(cmdLine, listId));
    builder.taskJsonFile(parser.getTaskJson(cmdLine, listId));
    builder.referenceJsonFile(parser.getReferenceJson(cmdLine, listId));
    builder.pvPerfOrd(parser.getPvPref(cmdLine));
    builder.userAgent(parser.getUserAgent(cmdLine));


    return new AppConfig(builder.build());
  }
}
