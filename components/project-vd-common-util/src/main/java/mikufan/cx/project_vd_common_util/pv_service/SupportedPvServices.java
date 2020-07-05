package mikufan.cx.project_vd_common_util.pv_service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mikufan.cx.common_vocaloid_entity.pv.service.PvServiceStr;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.ImmutableSet;

/**
 * telling currently what type of pv service is supported to download pvs
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SupportedPvServices {
  private static final ImmutableSet<String> AVAIABLE_PV_SERVICES
      = Sets.immutable.with(PvServiceStr.NICONICO, PvServiceStr.YOUTUBE, PvServiceStr.BILIBILI);


  public static boolean contains(String service){
    return AVAIABLE_PV_SERVICES.contains(service);
  }

  public static ImmutableSet<String> getSupportedPvServices(){
    return AVAIABLE_PV_SERVICES;
  }
}
