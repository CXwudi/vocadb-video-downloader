package mikufan.cx.project_vd_common_util.pv_service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mikufan.cx.common_vocaloid_entity.pv.service.PvServiceStr;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;

/**
 * telling currently what type of pv service is supported to download pvs
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SupportedPvServices {
  private static final ImmutableList<String> AVAIABLE_PV_SERVICES
      = Lists.immutable.with(PvServiceStr.NICONICO, PvServiceStr.YOUTUBE, PvServiceStr.BILIBILI);
  private static final ImmutableSet<String> AVAIABLE_PV_SERVICES_SET = Sets.immutable.withAll(AVAIABLE_PV_SERVICES);


  public static boolean contains(String service){
    return AVAIABLE_PV_SERVICES_SET.contains(service);
  }

  /**
   * a list of supported pv services, which is also the default order of pv service preference
   * @return
   */
  public static ImmutableList<String> getSupportedPvServices(){
    return AVAIABLE_PV_SERVICES;
  }
}
