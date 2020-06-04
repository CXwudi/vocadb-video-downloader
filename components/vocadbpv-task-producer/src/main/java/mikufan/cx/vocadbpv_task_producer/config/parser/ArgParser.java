package mikufan.cx.vocadbpv_task_producer.config.parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadbpv_task_producer.config.entity.AppConfig;
import mikufan.cx.vocadbpv_task_producer.config.entity.UserConfig;
import mikufan.cx.vocadbpv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadbpv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.apache.commons.cli.*;

import java.util.Arrays;

/**
 * the stateless arg parser
 * noted that lots of function are declared protected to explore it for junit
 * @author CX无敌
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArgParser {
  /**
   * singleton instance of this class
   */
  protected static final ArgParser PARSER = new ArgParser();

  @SneakyThrows(VocaDbPvTaskException.class)
  public static AppConfig parse(String[] args){
    //construct options
    var options = OptionsFactory.createOptions();

    //real parsing
    CommandLine cmdLine = PARSER.realParse(args, options);
    //if just need to print help
    PARSER.printHelpCheck(options, cmdLine);

    var builder = UserConfig.builder();
    builder.listId(PARSER.getListIdOrThrow(cmdLine, options));
    //todo: implement the rest

    return new AppConfig(builder.build());
  }

  private void printHelpCheck(Options options, CommandLine cmdLine) throws VocaDbPvTaskException {
    if (cmdLine.hasOption(OptionName.HELP.getOptName())){
      PARSER.printHelp(options);
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MITA0002, "Only printing help message");
    }
  }

  private int getListIdOrThrow(CommandLine cmdLine, Options options) throws VocaDbPvTaskException {
    if (!cmdLine.hasOption(OptionName.LIST_ID.getOptName())){
      printHelp(options);
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MITA0003, "Plz give me the ID of VocaDB favourite list");
    }
    var listIdStr = cmdLine.getOptionValue(OptionName.LIST_ID.getOptName());
    try {
      return Integer.parseInt(listIdStr);
    } catch (NumberFormatException e){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MITA0004, "Invalid ID of VocaDB favourite list, " + listIdStr, e);
    }
  }

  private CommandLine realParse(String[] args, Options options) throws VocaDbPvTaskException {
    var parser = new DefaultParser();
    CommandLine cmdLine;
    try {
      log.debug("command line = {}", Arrays.toString(args));
      cmdLine = parser.parse(options, args);
    } catch (ParseException e) {
      printHelp(options);
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MITA0001, "Fail to parse arguments, see \"Caused by\"", e);
    }
    return cmdLine;
  }

  private void printHelp(Options options) {
    var formatter = new HelpFormatter();
    formatter.printHelp("vocadbpv-task-producer", options);
  }


}
