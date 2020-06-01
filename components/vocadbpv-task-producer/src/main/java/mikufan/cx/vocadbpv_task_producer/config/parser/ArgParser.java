package mikufan.cx.vocadbpv_task_producer.config.parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadbpv_task_producer.config.entity.AppConfig;
import mikufan.cx.vocadbpv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadbpv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.apache.commons.cli.*;

/**
 *
 * @author CX无敌
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArgParser {

  @SneakyThrows
  public static AppConfig parse(String[] args){
    var options = createOptions();
    var parser = new DefaultParser();
    CommandLine cmdLine = null;
    try {
      cmdLine = parser.parse(options, args);
    } catch (ParseException e) {
      log.error("ParseException during args parsing", e);
      printHelpAndQuit(options);
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MITA0001, "Fail to parse arguments", e);
    }
    if (cmdLine.hasOption("help")){
      printHelpAndQuit(options);
      System.exit(3);
    }

    //todo: implement
//    var builder = UserConfig.builder();
//    builder.name("");
//    var config = new AppConfig(builder.build());
    return null;
  }

  private static void printHelpAndQuit(Options options) {
    var formatter = new HelpFormatter();
    formatter.printHelp("vocadbpv-task-producer", options);
  }


  protected static Options createOptions(){ // leave it protected to allow testing
    var options = new Options();
    options.addOption(new Option("help", "print this help message"));
    options.addOption(Option.builder(ArgumentsConstance.listIdOptStr)
        .longOpt(ArgumentsConstance.listIdOptLongStr)
        .hasArg()
        .argName("list id")
        .desc("Which VocaDB favourite list does this task based on, input the id")
        .build());

    options.addOption(Option.builder(ArgumentsConstance.taskNameOptStr)
        .longOpt(ArgumentsConstance.taskNameOptLongStr)
        .hasArg()
        .argName("name")
        .desc("(Optional) The name of this task if defined, else the name is task-<list id> " +
            "if the task doesn't have a name yet")
        .build());

    options.addOption(Option.builder(ArgumentsConstance.taskFileOptStr)
        .longOpt(ArgumentsConstance.taskFileOptLongStr)
        .hasArg()
        .argName("file")
        .desc("The location of the task json file to be stored or updated")
        .build());

    options.addOption(Option.builder(ArgumentsConstance.referenceFileOptStr)
        .longOpt(ArgumentsConstance.referenceFileOptLongStr)
        .hasArg()
        .argName("file")
        .desc("The location of the reference json file to be stored or updated. " +
            "This file is used to store raw json response from calling VocaDB RestAPI")
        .build());

    return options;
  }
}
