package mikufan.cx.vocadb_pv_task_producer.config.parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mikufan.cx.common_vocaloid_entity.pv.PvService;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * A factory declaring options
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class OptionsFactory {

  Options createOptions(){ // leave it package private to allow testing
    var options = new Options();
    var defaultSampleFileName = getDefaultTaskFileName(null);
    var defaultSampleTaskName = getDefaultTaskName(null);
    var defaultSampleRefFileName = getDefaultRefFileName(null);

    options.addOption(new Option(OptionName.HELP.getOptName(), "print this help message"));

    // first mandatory option needed for non -help use
    options.addOption(Option.builder(OptionName.LIST_ID.getOptName())
        .longOpt(OptionName.LIST_ID.getOptLongName())
        .hasArg()
        .argName(OptionName.LIST_ID.getArgName())
        .desc("Which VocaDB favourite list does this task based on, input the id")
        .build()); //we didn't make this required option as it will break the -help usage

    // second mandatory option needed for non -help use
    options.addOption(Option.builder(OptionName.USER_AGENT.getOptName())
        .longOpt(OptionName.USER_AGENT.getOptLongName())
        .hasArg()
        .argName(OptionName.USER_AGENT.getArgName())
        .desc("I am sorry, but you have to input your own user-agent, we don't provide it. " +
            "(so that ban by improper use of this software won't affect all other users). " +
            "Base on VocaDB Admin's advice, it's better to provide your VocaDB username"
        )
        .build());

    options.addOption(Option.builder(OptionName.TASK_NAME.getOptName())
        .longOpt(OptionName.TASK_NAME.getOptLongName())
        .hasArg()
        .argName(OptionName.TASK_NAME.getArgName())
        .desc(String.format("The name of this task. If not defined, name is \"%s\"", defaultSampleFileName))
        .build());

    options.addOption(Option.builder(OptionName.TASK_FILE.getOptName())
        .longOpt(OptionName.TASK_FILE.getOptLongName())
        .hasArg()
        .argName(OptionName.TASK_FILE.getArgName())
        .desc(String.format(
            "The relative or absolute path of the task json file to be created or updated. " +
            "This file is the main file to store PVs that need to download and PVs that are already downloaded" +
            "If not defined, -%s ./%s.json is assumed as the option",
            OptionName.TASK_FILE.getOptName(), defaultSampleTaskName))
        .build());

    options.addOption(Option.builder(OptionName.REFERENCE_FILE.getOptName())
        .longOpt(OptionName.REFERENCE_FILE.getOptLongName())
        .hasArg()
        .argName(OptionName.REFERENCE_FILE.getArgName())
        .desc(String.format(
            "The relative or absolute path of the reference json file to be created or updated. " +
            "This file is used to store raw json response from calling VocaDB RestAPI. " +
            "It contains useful information about songs involve in the task json file. " +
            "If not defined, -%s ./%s.json is assumed as the option",
            OptionName.REFERENCE_FILE.getOptName(), defaultSampleRefFileName))
        .build());

    options.addOption(Option.builder(OptionName.PV_PREFERENCE.getOptName())
        .longOpt(OptionName.PV_PREFERENCE.getOptLongName())
        .hasArgs()
        //without this, we can make -p arg1 arg2 ...
        //with common, we make -p arg1,arg2
        .valueSeparator(',')
        .argName(OptionName.PV_PREFERENCE.getArgName())
        .desc(String.format("Your preference ranking of pv websites, separated by comma, " +
                "by default this is \"-%s %s\" (no space around comma)" +
                "Currently we only support websites listed in the default order",
            OptionName.PV_PREFERENCE.getOptName(),
            PvService.getDefaultOrder().makeString(",")))
        .build()
    );

    return options;
  }

  static String getDefaultTaskFileName(String listId) {
    if (listId == null){
      return String.format("task_for_list_<%s>", OptionName.TASK_FILE.getArgName());
    } else {
      return String.format("task_for_list_%s", listId);
    }
  }

  static String getDefaultTaskName(String listId) {
    if (listId == null){
      return String.format("task<%s>", OptionName.TASK_NAME.getArgName());
    } else {
      return String.format("task%s", listId);
    }
  }

  static String getDefaultRefFileName(String ref){
    if (ref == null){
      return String.format("vocadb_list_<%s>_ref", OptionName.REFERENCE_FILE.getArgName());
    } else {
      return String.format("vocadb_list_%s_ref", ref);
    }
  }
}
