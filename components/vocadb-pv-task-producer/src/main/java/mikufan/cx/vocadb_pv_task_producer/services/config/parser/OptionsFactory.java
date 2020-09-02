package mikufan.cx.vocadb_pv_task_producer.services.config.parser;

import lombok.NoArgsConstructor;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * A factory declaring options
 * @author CX无敌
 */
@NoArgsConstructor
@Component @Lazy
public final class OptionsFactory {

  public Options createOptions(){ // leave it package private to allow testing
    var options = new Options();
//    var defaultSampleFileName = getDefaultTaskFileName(null);
//    var defaultSampleTaskName = getDefaultTaskName(null);
//    var defaultSampleRefFileName = getDefaultRefFileName(null);

    options.addOption(new Option(OptionName.HELP.getOptName(), "print this help message"));

    // first mandatory option with required argument needed for non -help use
    options.addOption(Option.builder(OptionName.LIST_ID.getOptName())
        .longOpt(OptionName.LIST_ID.getOptLongName())
        .hasArg()
        .required()
        .argName(OptionName.LIST_ID.getArgName())
        .desc("Which VocaDB favourite list does this task based on, input the id")
        .build());

    // second mandatory option with required argument needed for non -help use
    options.addOption(Option.builder(OptionName.OUTPUT_DIR.getOptName())
        .longOpt(OptionName.OUTPUT_DIR.getOptLongName())
        .hasArg()
        .required()
        .argName(OptionName.OUTPUT_DIR.getArgName())
        .desc("The path of the directory where all songs' info in json format will be stored")
        .build());

    // third mandatory option with required argument needed for non -help use
    options.addOption(Option.builder(OptionName.USER_CONFIG.getOptName())
        .longOpt(OptionName.USER_CONFIG.getOptLongName())
        .hasArg()
        .required()
        .argName(OptionName.USER_CONFIG.getArgName())
        .desc("The location of your user config file")
        .build());

//    options.addOption(Option.builder(OptionName.PV_PREFERENCE.getOptName())
//        .longOpt(OptionName.PV_PREFERENCE.getOptLongName())
//        .hasArgs()
//        //without this, we can make -p arg1 arg2 ...
//        //with common, we make -p arg1,arg2
//        .valueSeparator(',')
//        .argName(OptionName.PV_PREFERENCE.getArgName())
//        .desc(String.format("Your preference ranking of pv websites, separated by comma, " +
//                "by default this is \"-%s %s\" (no space around comma)" +
//                "Currently we only support websites listed in the default order",
//            OptionName.PV_PREFERENCE.getOptName(),
//            SupportedPvServices.getSupportedPvServices().makeString(",")))
//        .build()
//    );

    return options;
  }
//
//  static String getDefaultTaskFileName(String listId) {
//    if (listId == null){
//      return String.format("task_for_list_<%s>", OptionName.TASK_FILE.getArgName());
//    } else {
//      return String.format("task_for_list_%s", listId);
//    }
//  }
//
//  static String getDefaultTaskName(String listId) {
//    if (listId == null){
//      return String.format("task<%s>", OptionName.TASK_NAME.getArgName());
//    } else {
//      return String.format("task%s", listId);
//    }
//  }
//
//  static String getDefaultRefFileName(String ref){
//    if (ref == null){
//      return String.format("vocadb_list_<%s>_ref", OptionName.REFERENCE_FILE.getArgName());
//    } else {
//      return String.format("vocadb_list_%s_ref", ref);
//    }
//  }
}
