package mikufan.cx.vocadbpv_task_producer.config.parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mikufan.cx.common_entity.common.PvService;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * A factory declaring options
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionsFactory {

  static Options createOptions(){ // leave it package private to allow testing
    var options = new Options();
    var defaultName = String.format("task<%s>", OptionName.LIST_ID.getArgName());
    options.addOption(new Option(OptionName.HELP.getOptName(), "print this help message"));
    options.addOption(Option.builder(OptionName.LIST_ID.getOptName())
        .longOpt(OptionName.LIST_ID.getOptLongName())
        .hasArg()
        .argName(OptionName.LIST_ID.getArgName())
        .desc("Which VocaDB favourite list does this task based on, input the id")
        .build()); //we didn't make this required option as it will break the -help usage

    options.addOption(Option.builder(OptionName.TASK_NAME.getOptName())
        .longOpt(OptionName.TASK_NAME.getOptLongName())
        .hasArg()
        .argName(OptionName.TASK_NAME.getArgName())
        .desc(String.format("The name of this task. If not defined, name is \"%s\"", defaultName))
        .build());

    options.addOption(Option.builder(OptionName.TASK_FILE.getOptName())
        .longOpt(OptionName.TASK_FILE.getOptLongName())
        .hasArg()
        .argName(OptionName.TASK_FILE.getArgName())
        .desc(String.format(
            "The relative or absolute path of the task json file to be stored or updated. " +
            "If not defined, -%s ./%s.json is assumed as the option",
            OptionName.TASK_FILE.getOptName(), defaultName))
        .build());

    options.addOption(Option.builder(OptionName.REFERENCE_FILE.getOptName())
        .longOpt(OptionName.REFERENCE_FILE.getOptLongName())
        .hasArg()
        .argName(OptionName.REFERENCE_FILE.getArgName())
        .desc(String.format(
            "The relative or absolute path of the reference json file to be stored or updated. " +
            "This file is used to store raw json response from calling VocaDB RestAPI. " +
            "If not defined, -%s ./%s-ref.json is assumed as the option",
            OptionName.REFERENCE_FILE.getOptName(), defaultName))
        .build());

    options.addOption(Option.builder(OptionName.PV_PREFERENCE.getOptName())
        .longOpt(OptionName.PV_PREFERENCE.getOptLongName())
        .hasArgs()
        .argName(OptionName.PV_PREFERENCE.getArgName())
        .desc(String.format("Your preference ranking of pv websites, separated by comma, " +
                "by default this is -%s %s,%s,%s " +
                "Currently we only support websites listed in the default order",
            OptionName.PV_PREFERENCE.getOptName(),
            PvService.NICONICO.getServiceName(),
            PvService.YOUTUBE.getServiceName(),
            PvService.BILIBILI.getServiceName()))
        .build()
    );

    return options;
  }
}
