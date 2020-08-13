package mikufan.cx.vocadb_pv_downloader.config.validator;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.vocadb_pv_downloader.config.entity.UserConfig;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;

/**
 * a class containing external validation for {@link UserConfig}
 * @author CX无敌
 */
@Slf4j
public class UserConfigValidator {

  /**
   * validate and fill up optional parts
   * @param config config parsed by jackson
   * @return fixed user config
   * @throws VocaDbPvDlException if a validation is violated
   */
  public UserConfig validateAndFill(@NonNull UserConfig config) throws VocaDbPvDlException {

    return config;
  }
}
