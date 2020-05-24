package mikufan.cx.common.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.ImmutableMap;

import java.util.Objects;

/**
 * enum of pv service that currently supported
 * @author CX无敌
 */
@AllArgsConstructor
@Getter
public enum PvService {

  @JsonProperty(PvServiceString.NICONICO_STR)
  NICONICO(PvServiceString.NICONICO_STR),

  @JsonProperty(PvServiceString.YOUTUBE_STR)
  YOUTUBE(PvServiceString.YOUTUBE_STR),

  @JsonProperty(PvServiceString.BILIBILI_STR)
  BILIBILI(PvServiceString.BILIBILI_STR);

  private final String serviceName;

  /**
   * map to hold service name -> enum, construct at jvm startup
   */
  private static final ImmutableMap<String, PvService> NAME_TO_ENUM_MAP;

  static {
    NAME_TO_ENUM_MAP = Maps.immutable.of(
        PvServiceString.NICONICO_STR, NICONICO,
        PvServiceString.YOUTUBE_STR, YOUTUBE,
        PvServiceString.BILIBILI_STR, BILIBILI
        );
  }

  /**
   * get the enum of pv service website base on service name. <br/>
   * note: remember to call {@link PvService#contain(String)} before calling this method
   */
  public static PvService enumOf(String serviceName){
    return Objects.requireNonNull(NAME_TO_ENUM_MAP.get(serviceName), "Unknown pv website: " + serviceName);
  }

  public static boolean contain(String serviceName){
    return NAME_TO_ENUM_MAP.containsKey(serviceName);
  }

  /**
   * holding the name of pv website, used by this enum only
   */
  private interface PvServiceString {
    String NICONICO_STR = "NicoNicoDouga";
    String YOUTUBE_STR = "Youtube";
    String BILIBILI_STR = "Bilibili";
  }
}
