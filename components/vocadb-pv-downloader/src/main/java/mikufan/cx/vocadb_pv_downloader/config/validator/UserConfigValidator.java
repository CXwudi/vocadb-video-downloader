package mikufan.cx.vocadb_pv_downloader.config.validator;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.project_vd_common_util.pv_service.SupportedPvServices;
import mikufan.cx.vocadb_pv_downloader.config.entity.UserConfig;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlRCI;

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
    var pvPref = config.getPvPreference();
    var supportedPvs = SupportedPvServices.getSupportedPvServices().toSet();
    for (var service : pvPref){
      if (!supportedPvs.contains(service)){
        throw new VocaDbPvDlException(VocaDbPvDlRCI.MIKU_DL_010,
            "An unsupported Pv service website is decleared in pvPreference list: " + service);
      }
    }

    return config;
  }
}
