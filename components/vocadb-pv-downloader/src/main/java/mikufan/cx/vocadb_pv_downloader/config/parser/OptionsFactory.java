package mikufan.cx.vocadb_pv_downloader.config.parser;

import lombok.NoArgsConstructor;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import static mikufan.cx.vocadb_pv_downloader.config.parser.OptionName.*;
/**
 * A factory declaring options
 * @author CX无敌
 */
@NoArgsConstructor
public class OptionsFactory {

  public Options createOptions(){
    var options = new Options();

    options.addOption(new Option(HELP.getOptName(), "print this help message"));

    //first mandatory option, input dir
    options.addOption(Option.builder(INPUT_DIR.getOptName())
        .longOpt(INPUT_DIR.getOptLongName())
        .required()
        .hasArg()
        .argName(INPUT_DIR.getArgName())
        .desc("The path of the input dir, which is the output dir of vocadb-pv-task-producer " +
            ", contains json formatted infos of songs that needed to be downloaded")
        .build());

    //first mandatory option, output dir
    options.addOption(Option.builder(OUTPUT_DIR.getOptName())
        .longOpt(OUTPUT_DIR.getOptLongName())
        .required()
        .hasArg()
        .argName(OUTPUT_DIR.getArgName())
        .desc("The output dir to store all downloaded PVs, thumbnails, and json infos")
        .build());

    //third mandatory option, user config file
    options.addOption(Option.builder(USER_CONFIG.getOptName())
        .longOpt(USER_CONFIG.getOptLongName())
        .required()
        .hasArg()
        .argName(USER_CONFIG.getArgName())
        .desc("The location of your user config file")
        .build());

    return options;
  }
}
