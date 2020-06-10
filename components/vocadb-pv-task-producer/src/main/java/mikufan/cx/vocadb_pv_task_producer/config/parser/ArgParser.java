package mikufan.cx.vocadb_pv_task_producer.config.parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_entity.pv.PvService;
import mikufan.cx.common_util.exception.SneakyThrow;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;
import org.apache.commons.cli.*;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

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
   * parsing {@link OptionName#LIST_ID} <br/>
   * this method can sneaky throw {@link VocaDbPvTaskException}
   */
  int getListIdOrThrow(CommandLine cmdLine, Options options) throws VocaDbPvTaskException {
    Function<String, Integer> function = listIdStr -> {
      try {
        return Integer.parseInt(listIdStr);
      } catch (NumberFormatException e){
        return SneakyThrow.theException(new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_004, "Invalid ID of VocaDB favourite list, " + listIdStr, e));
      }
    };
    Supplier<Integer> throwExpSupplier = () -> {
      printHelp(options);
      return SneakyThrow.theException(new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_003, "Plz give me the ID of VocaDB favourite list"));
    };
    return getValueOrElse(cmdLine, OptionName.LIST_ID, function, throwExpSupplier);
  }

  /**
   * parse {@link OptionName#USER_AGENT} <br/>
   * this method can sneaky throw {@link VocaDbPvTaskException}
   */
  String getUserAgent(CommandLine cmdLine) throws VocaDbPvTaskException{
    Supplier<String> throwExpSupplier =
        () -> SneakyThrow.theException(new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_006,
            "Plz define your own user-agent for the HttpClient to retrieve VocaDB song list"));
    return getValueOrElse(cmdLine, OptionName.USER_AGENT, Function.identity(), throwExpSupplier);
  }

  /**
   * parsing {@link OptionName#TASK_NAME}
   */
  String getTaskName(CommandLine cmdLine, int listId) {
    return getValueOrElse(cmdLine, OptionName.TASK_NAME, Function.identity(),
        () -> OptionsFactory.getDefaultTaskName("" + listId));

  }

  /**
   * parsing {@link OptionName#TASK_FILE}
   */
  Path getTaskJson(CommandLine cmdLine, int listId) throws VocaDbPvTaskException{
    Function<String, Path> function = fileName -> {
      var path = Path.of(fileName);
      if (Files.isDirectory(path)){
        return SneakyThrow.theException(new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_008,
            "the path denoted by the " + OptionName.TASK_FILE.getOptName() + "is a directory: " + path));
      } else {
        return path;
      }
    };

    Supplier<Path> defaultPath = () -> {
      var fileName = OptionsFactory.getDefaultTaskFileName("" + listId);
      return Path.of(fileName + ".json");
    };
    return getValueOrElse(cmdLine, OptionName.TASK_FILE, function, defaultPath);
  }

  /**
   * parsing {@link OptionName#REFERENCE_FILE}
   */
  Path getReferenceJson(CommandLine cmdLine, int listId) throws VocaDbPvTaskException {
    Function<String, Path> function = fileName -> {
      var path = Path.of(fileName);
      if (Files.isDirectory(path)){
        return SneakyThrow.theException(new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_007,
            "the path denoted by the " + OptionName.REFERENCE_FILE.getOptName() + "is a directory: " + path));
      } else {
        return path;
      }
    };
    Supplier<Path> defaultPath = () -> {
      var fileName = OptionsFactory.getDefaultRefFileName("" + listId);
      return Path.of(fileName + ".json");
    };
    return getValueOrElse(cmdLine, OptionName.REFERENCE_FILE, function, defaultPath);
  }

  /**
   * parsing {@link OptionName#PV_PREFERENCE} <br/>
   * throw {@link VocaDbPvTaskException} if unsupported pv is passed in
   *
   */
  ImmutableList<PvService> getPvPref(CommandLine cmdLine) throws VocaDbPvTaskException {
    Function<String[], ImmutableList<PvService>> function = prefArr -> {
      //here we didn't do the check of pv string, instead we try catch later
      var knownOrder = Lists.mutable.of(prefArr)
          .collect(PvService::enumOf);

      Lists.mutable.of(PvService.values())
          //this introduce O(N^2)
          .rejectWith((service, order) -> order.contains(service), knownOrder)
          .injectInto(knownOrder, (order, service) -> {
            order.add(service);
            return order;
          });

      return knownOrder.toImmutable();
    };
    try {
      return getValuesOrElse(cmdLine, OptionName.PV_PREFERENCE, function, PvService::getDefaultOrder);
    } catch (NullPointerException e){
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_005,
          "Un-supported PV website in PV preference order, see \"Caused by\"", e);
    }
  }

  /**
   * an util method for retrieving an arg from the given option in cmd line, and processing that arg string with given function. <br/>
   * if the option doesn't exist, return a default value produced by defValSupplier
   */
  private <T> T getValueOrElse(CommandLine cmdLine, OptionName opt,
                               Function<String, T> process,
                               Supplier<T> defValSupplier) {
    return getOrElse(cmdLine, opt, process, CommandLine::getOptionValue, defValSupplier);
  }

  /**
   * an util method for retrieving a list of args from the given option in cmd line, and processing those args with given function. <br/>
   * if the option doesn't exist, return a default value produced by defValSupplier
   */
  private <T> T getValuesOrElse(CommandLine cmdLine, OptionName opt,
                                Function<String[], T> process,
                                Supplier<T> defValSupplier){
    return getOrElse(cmdLine, opt, process, CommandLine::getOptionValues, defValSupplier);
  }

  /**
   * the internal util method used by {@link ArgParser#getValuesOrElse(org.apache.commons.cli.CommandLine, mikufan.cx.vocadb_pv_task_producer.config.parser.OptionName, java.util.function.Function, java.util.function.Supplier)}
   * and {@link ArgParser#getValuesOrElse(org.apache.commons.cli.CommandLine, mikufan.cx.vocadb_pv_task_producer.config.parser.OptionName, java.util.function.Function, java.util.function.Supplier)}
   */
  private <I, T> T getOrElse(CommandLine cmdLine, OptionName opt,
                             Function<I, T> process,
                             BiFunction<CommandLine, String, I> readArg,
                             Supplier<T> defValSupplier) {
    if (cmdLine.hasOption(opt.getOptName())){
      return process.apply(readArg.apply(cmdLine, opt.getOptName()));
    } else {
      return defValSupplier.get();
    }
  }

}
