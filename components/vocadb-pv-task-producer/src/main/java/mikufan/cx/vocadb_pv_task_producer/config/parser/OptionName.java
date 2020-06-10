package mikufan.cx.vocadb_pv_task_producer.config.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * hold constance name and declaration of various options and arguments name
 * @author CX无敌
 */
@AllArgsConstructor @Getter
public enum OptionName {
  LIST_ID("i", "list-id", "list-id"),
  TASK_NAME("tn", "task-name", "name"),
  TASK_FILE("f", "task-file", "task-file"),
  REFERENCE_FILE("r", "reference-file", "ref-file"),
  PV_PREFERENCE("p","pv-preference", "websites"),
  USER_AGENT("ua", "user-agent", "user agent"),

  HELP("help", null, null);

  private final String optName;
  private final String optLongName;
  private final String argName;

}
