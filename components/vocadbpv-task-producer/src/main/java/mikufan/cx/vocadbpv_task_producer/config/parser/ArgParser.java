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
    var options = PARSER.createOptions();
    //real parsing
    CommandLine cmdLine = PARSER.realParse(args, options);

    //check and get listId
    int listId = PARSER.getListId(cmdLine);
    //todo: implement the rest

    //building
    var builder = UserConfig.builder();
    builder.listId(listId);
    //todo: implement the rest
    return new AppConfig(builder.build());
  }

  private int getListId(CommandLine cmdLine) throws VocaDbPvTaskException {
    int listId;
    var listIdStr = cmdLine.getOptionValue(OptionName.LIST_ID.getOptName());
    try {
      listId = Integer.parseInt(listIdStr);
    } catch (NumberFormatException e){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MITA0002, "Invalid ID of VocaDB favourite list");
    }
    return listId;
  }

  private CommandLine realParse(String[] args, Options options) throws VocaDbPvTaskException {
    var parser = new DefaultParser();
    CommandLine cmdLine;
    try {
      log.debug("command line = {}", Arrays.toString(args));
      cmdLine = parser.parse(options, args);
    } catch (ParseException e) {
      PARSER.printHelp(options);
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MITA0001, "Fail to parse arguments, see \"Caused by\"", e);
    }
    if (cmdLine.hasOption(OptionName.HELP.getOptName())){
      PARSER.printHelp(options);
      System.exit(3);
    }
    return cmdLine;
  }

  protected void printHelp(Options options) {
    var formatter = new HelpFormatter();
    formatter.printHelp("vocadbpv-task-producer", options);
  }


  protected Options createOptions(){ // leave it protected to allow testing
    var options = new Options();
    var defaultName = String.format("task<%s>", OptionName.LIST_ID.getArgName());
    options.addOption(new Option(OptionName.HELP.getOptName(), "print this help message"));
    options.addOption(Option.builder(OptionName.LIST_ID.getOptName())
        .longOpt(OptionName.LIST_ID.getOptLongName())
        .hasArg()
        .argName(OptionName.LIST_ID.getArgName())
        .desc("Which VocaDB favourite list does this task based on, input the id")
        .build());

    options.addOption(Option.builder(OptionName.TASK_NAME.getOptName())
        .longOpt(OptionName.TASK_NAME.getOptLongName())
        .optionalArg(true)
        .numberOfArgs(1)
        .argName(OptionName.TASK_NAME.getArgName())
        .desc(String.format("The name of this task. If not defined, name is \"%s\"", defaultName))
        .build());

    options.addOption(Option.builder(OptionName.TASK_FILE.getOptName())
        .longOpt(OptionName.TASK_FILE.getOptLongName())
        .optionalArg(true)
        .numberOfArgs(1)
        .argName(OptionName.TASK_FILE.getArgName())
        .desc(String.format(
            "The relative or absolute path of the task json file to be stored or updated. " +
            "If not defined, -%s ./%s.json is assumed as the option",
            OptionName.TASK_FILE.getOptName(), defaultName))
        .build());

    options.addOption(Option.builder(OptionName.REFERENCE_FILE.getOptName())
        .longOpt(OptionName.REFERENCE_FILE.getOptLongName())
        .optionalArg(true)
        .numberOfArgs(1)
        .argName(OptionName.REFERENCE_FILE.getArgName())
        .desc(String.format(
            "The relative or absolute path of the reference json file to be stored or updated. " +
            "This file is used to store raw json response from calling VocaDB RestAPI. " +
            "If not defined, -%s ./%s-ref.json is assumed as the option",
            OptionName.REFERENCE_FILE.getOptName(), defaultName))
        .build());

    return options;
  }
}
