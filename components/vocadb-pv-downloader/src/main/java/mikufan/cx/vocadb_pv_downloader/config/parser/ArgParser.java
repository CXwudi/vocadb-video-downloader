package mikufan.cx.vocadb_pv_downloader.config.parser;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.project_vd_common_util.cli.parser.ParserUtil;
import mikufan.cx.project_vd_common_util.exception.ThrowableFunction;
import mikufan.cx.project_vd_common_util.exception.ThrowableSupplier;
import mikufan.cx.project_vd_common_util.io.JacksonPojoTransformer;
import mikufan.cx.project_vd_common_util.jackson.YamlMapperUtil;
import mikufan.cx.vocadb_pv_downloader.config.entity.UserConfig;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlRCI;
import org.apache.commons.cli.*;
import org.eclipse.collections.api.factory.Lists;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author CX无敌
 */
@Slf4j
@NoArgsConstructor
public class ArgParser {
  
  /**
   * the real argument parsing code, using commons-cli
   * @param args args from main()
   * @param options all available options
   * @return the parsed {@link CommandLine} which contains all information
   * @throws VocaDbPvDlException if parsing fails
   */
  public CommandLine parseArgs(String[] args, Options options) throws VocaDbPvDlException {
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
      throw new VocaDbPvDlException(VocaDbPvDlRCI.MIKU_DL_001, "Fail to parse arguments, see \"Caused by\"", e);
    }
    return cmdLine;
  }


  /**
   * if the command is just -help, then just print help and quit the program, otherwise do nothing
   * @param options all options
   * @param cmdLine parsed command line
   * @throws VocaDbPvDlException which terminates this program
   */
  public void checkAndPrintHelpAndQuitIfNeed(Options options, CommandLine cmdLine) throws VocaDbPvDlException {
    if (cmdLine.hasOption(OptionName.HELP.getOptName())){
      printHelp(options);
      throw new VocaDbPvDlException(VocaDbPvDlRCI.MIKU_DL_009, "Only printing help message");
    }
  }

  public void printHelp(Options options) {
    var formatter = new HelpFormatter();
    formatter.printHelp("vocadb-pv-task-producer", options);
  }


  /**
   * parse {@link OptionName#OUTPUT_DIR} <br/>
   * this method can sneaky throw {@link VocaDbPvDlException}
   */
  public Path getOutputDirOrThrow(CommandLine cmdLine) {
    ThrowableFunction<String, Path> function = dirName -> {
      var path = Path.of(dirName);
      if (Files.isDirectory(path)){
        return path;
      } else {
        throw new VocaDbPvDlException(VocaDbPvDlRCI.MIKU_DL_004,
            "the path denoted by the " + OptionName.OUTPUT_DIR.getOptName() + "is NOT an existing directory: " + path);
      }
    };

    ThrowableSupplier<Path> defaultPath = () -> {
      throw new VocaDbPvDlException(VocaDbPvDlRCI.MIKU_DL_005,
          OptionName.OUTPUT_DIR.getOptName() + " should contain one argument");
    };

    return ParserUtil.getValueOrElse(cmdLine, OptionName.OUTPUT_DIR.getOptName(), function.toFunction(), defaultPath.toSupplier());
  }


  /**
   * parse {@link OptionName#USER_CONFIG} <br/>
   * this method can sneaky throw {@link VocaDbPvDlException}
   */
  public UserConfig getUserConfigOrThrow(CommandLine cmdLine) {
    ThrowableFunction<String, UserConfig> function = fileName -> {
      var path = Path.of(fileName);
      if (Files.isRegularFile(path)){
        var reader = JacksonPojoTransformer.createWith(YamlMapperUtil.createDefaultForReadOnly(), UserConfig.class);
        return reader.read(path);
      } else {
        throw new VocaDbPvDlException(VocaDbPvDlRCI.MIKU_DL_006,
            "the path denoted by the " + OptionName.USER_CONFIG.getOptName() + "is NOT an existing directory: " + path);
      }
    };

    ThrowableSupplier<UserConfig> defaultPath = () -> {
      throw new VocaDbPvDlException(VocaDbPvDlRCI.MIKU_DL_007,
          OptionName.USER_CONFIG.getOptName() + " should contain one argument");
    };

    return ParserUtil.getValueOrElse(cmdLine, OptionName.USER_CONFIG.getOptName(), function.toFunction(), defaultPath.toSupplier());
  }
}
