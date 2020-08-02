package mikufan.cx.project_vd_common_util.cli.parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.cli.CommandLine;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An util for encapsulating spoiler cli parsing code
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParserUtil {

  /**
   * an util method for retrieving an arg from the given option in cmd line, and processing that arg string with given function. <br/>
   * if the option doesn't exist in cmd line, return a default value produced by defValSupplier
   */
  public static <T> T getValueOrElse(CommandLine cmdLine, String optName,
                                     Function<String, T> process,
                                     Supplier<T> defValSupplier) {
    return getOrElse(cmdLine, optName, process, CommandLine::getOptionValue, defValSupplier);
  }

  /**
   * an util method for retrieving a list of args from the given option in cmd line, and processing those args with given function. <br/>
   * if the option doesn't exist in cmd line, return a default value produced by defValSupplier
   */
  public static <T> T getValuesOrElse(CommandLine cmdLine, String optName,
                                      Function<String[], T> process,
                                      Supplier<T> defValSupplier){
    return getOrElse(cmdLine, optName, process, CommandLine::getOptionValues, defValSupplier);
  }

  /**
   * the internal util method used by {@link #getValuesOrElse(CommandLine, String, Function, Supplier)}
   * and {@link #getValueOrElse(CommandLine, String, Function, Supplier)}
   */
  public static <I, T> T getOrElse(CommandLine cmdLine, String optName,
                                   Function<I, T> process,
                                   BiFunction<CommandLine, String, I> readArg,
                                   Supplier<T> defValSupplier) {
    if (cmdLine.hasOption(optName)){
      return process.apply(readArg.apply(cmdLine, optName));
    } else {
      return defValSupplier.get();
    }
  }
}
