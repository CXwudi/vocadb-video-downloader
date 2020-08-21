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
   * validate user config.
   * this function should not modify user config
   * @param config config parsed by jackson
   * @return fixed user config
   * @throws VocaDbPvDlException if a validation is violated
   */
  public UserConfig validate(@NonNull UserConfig config) throws VocaDbPvDlException {
    checkIfAllServiceSupported(config.getPvPreference());

    return config;
  }

  /**
   * check if all services defined in list are supported
   * @param services users' defined services
   * @throws VocaDbPvDlException if any of them are not supported
   */
  protected void checkIfAllServiceSupported(Iterable<String> services) throws VocaDbPvDlException {
    var supportedPvs = SupportedPvServices.getSupportedPvServices().toSet();
    for (var service : services){
      if (!supportedPvs.contains(service)){
        throw new VocaDbPvDlException(VocaDbPvDlRCI.MIKU_DL_010,
            "An unsupported Pv service website is declared in pvPreference list: " + service);
      }
    }
  }
}
