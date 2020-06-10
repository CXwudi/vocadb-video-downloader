package mikufan.cx.vocadb_pv_task_producer.config.parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_entity.pv.PvService;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.apache.commons.cli.*;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

import java.io.File;
import java.nio.file.Files;

/**
 * the stateless arg parser <br/>
 * noted functions are declared without modifier to be package-private
 * @author CX无敌
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ArgParser {
  /**
   * the real argument parsing code, using commons-cli
   * @param args args from main()
   * @param options all available options
   * @return the parsed {@link CommandLine} which contains all information
   * @throws VocaDbPvTaskException if parsing fails
   */
  CommandLine parseArgs(String[] args, Options options) throws VocaDbPvTaskException {
    var parser = new DefaultParser();
    CommandLine cmdLine;
    try {
      log.debug("command line = {}",
          Lists.fixedSize.of(args).asLazy()
              .collect(str -> "\"" + str + "\"")
              .makeString("[", ", ", "]"));
      cmdLine = parser.parse(options, args);
    } catch (ParseException e) {
      printHelp(options);
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_001, "Fail to parse arguments, see \"Caused by\"", e);
    }
    return cmdLine;
  }

  /**
   * if the command is just -help, then just print help and quit the program, otherwise do nothing
   * @param options all options
   * @param cmdLine parsed command line
   * @throws VocaDbPvTaskException which terminates this program
   */
  void checkAndPrintHelpAndQuitIfNeed(Options options, CommandLine cmdLine) throws VocaDbPvTaskException {
    if (cmdLine.hasOption(OptionName.HELP.getOptName())){
      printHelp(options);
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_002, "Only printing help message");
    }
  }

  void printHelp(Options options) {
    var formatter = new HelpFormatter();
    formatter.printHelp("vocadb-pv-task-producer", options);
  }

  /**
   * parsing {@link OptionName#LIST_ID}
   */
  int getListIdOrThrow(CommandLine cmdLine, Options options) throws VocaDbPvTaskException {
    if (!cmdLine.hasOption(OptionName.LIST_ID.getOptName())){
      printHelp(options);
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_003, "Plz give me the ID of VocaDB favourite list");
    }
    var listIdStr = cmdLine.getOptionValue(OptionName.LIST_ID.getOptName());
    try {
      return Integer.parseInt(listIdStr);
    } catch (NumberFormatException e){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_004, "Invalid ID of VocaDB favourite list, " + listIdStr, e);
    }
  }

  /**
   * parsing {@link OptionName#TASK_NAME}
   */
  String getTaskName(CommandLine cmdLine, int listId) {
    if (cmdLine.hasOption(OptionName.TASK_NAME.getOptName())){
      return cmdLine.getOptionValue(OptionName.TASK_NAME.getOptName());
    } else {
      return OptionsFactory.getDefaultTaskName("" + listId);
    }
  }

  /**
   * parsing {@link OptionName#TASK_FILE}
   */
  File getTaskJson(CommandLine cmdLine, int listId) throws VocaDbPvTaskException{
    if (cmdLine.hasOption(OptionName.TASK_FILE.getOptName())){
      var file = new File(cmdLine.getOptionValue(OptionName.TASK_FILE.getOptName()));
      if (Files.exists(file.toPath())){
        return file;
      } else {
        throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_005, "Task file doesn't exist: " + file);
      }
    } else { //create default
      var fileName = OptionsFactory.getDefaultTaskFileName("" + listId);
      return new File(fileName + ".json");
    }
  }

  /**
   * parsing {@link OptionName#REFERENCE_FILE}
   */
  File getReferenceJson(CommandLine cmdLine, int listId) throws VocaDbPvTaskException{
    if (cmdLine.hasOption(OptionName.REFERENCE_FILE.getOptName())){
      var file = new File(cmdLine.getOptionValue(OptionName.REFERENCE_FILE.getOptName()));
      if (Files.exists(file.toPath())){
        return file;
      } else {
        throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_006, "Task file doesn't exist: " + file);
      }
    } else { //create default
      var fileName = OptionsFactory.getDefaultRefFileName("" + listId);
      return new File(fileName + ".json");
    }
  }

  /**
   * parsing {@link OptionName#PV_PREFERENCE}
   */
  ImmutableList<PvService> getPvPref(CommandLine cmdLine) {
    return Lists.immutable.empty();
  }
}
