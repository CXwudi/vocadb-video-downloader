package mikufan.cx.vocadbpv_task_producer.config.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * hold constance name and declaration of various options and arguments name
 * @author CX无敌
 */
@AllArgsConstructor @Getter
public enum OptionName {
  LIST_ID("i", "list-id", "list-id"),
  TASK_NAME("name", "task-name", "name"),
  TASK_FILE("f", "task-file", "file"),
  REFERENCE_FILE("r", "reference-file", "file"),

  HELP("help", null, null);

  private final String optName;
  private final String optLongName;
  private final String argName;

}
