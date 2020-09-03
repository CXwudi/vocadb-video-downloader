package mikufan.cx.vocadb_pv_downloader.config.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * all available options for cmd in this module
 * @author CX无敌
 */
@AllArgsConstructor
@Getter
public enum OptionName {
  INPUT_DIR("i","input-dir", "input dir"),
  OUTPUT_DIR("o","output-dir", "output dir"),
  USER_CONFIG("s", "user-config", "user config"),

  HELP("help", null, null);

  private final String optName;
  private final String optLongName;
  private final String argName;
}
