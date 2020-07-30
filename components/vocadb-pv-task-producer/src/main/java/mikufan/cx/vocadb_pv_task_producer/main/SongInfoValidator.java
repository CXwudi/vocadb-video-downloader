package mikufan.cx.vocadb_pv_task_producer.main;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.vocadb.models.PVContract;
import mikufan.cx.common_vocaloid_entity.vocadb.models.SongForApiContract;
import mikufan.cx.project_vd_common_util.pv_service.SupportedPvServices;

import java.util.Optional;

/**
 * A stateless validator to check if a song is downloadable
 * @author CX无敌
 */
@Slf4j
@RequiredArgsConstructor
public class SongInfoValidator {

  /**
   * validate the song is downloadable or not base on pv info
   * @param song the song to be checked
   * @return optional of string indicate if error message exists or not
   */
  public Optional<String> validate(@NonNull SongForApiContract song){
    // get the list of PVs of a song
    var pvs = song.getPvs();
    var name = song.getName();

    //check if no pvs
    if (pvs == null || pvs.isEmpty()){
      log.warn("no PVs found in {}", name);
      return Optional.of("no official PVs found");
    }

    //check if pvs are inaccessible
    var accessiblePvs = pvs.reject(PVContract::isDisabled);
    if (accessiblePvs.isEmpty()){
      log.warn("all PVs for {} are not accessible on website", name);
      return Optional.of("all PVs are not accessible from service websites");
    }

    //check if pvs are not supported
    var supportedPvs = accessiblePvs.select(pv -> SupportedPvServices.contains(pv.getService()));
    if (supportedPvs.isEmpty()){
      var supportedServices = SupportedPvServices.getSupportedPvServices().makeString(", ");
      log.warn("current {} doesn't contains PVs from supported web services {}", name, supportedServices);
      return Optional.of("currently we are not able to download PV from web services other than " +
          supportedServices);
    }

    //pass
    return Optional.empty();
  }
}
