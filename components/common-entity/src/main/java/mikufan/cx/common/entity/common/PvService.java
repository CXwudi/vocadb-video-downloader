package mikufan.cx.common.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * enum of pv service that currently can be handled
 * @author CX无敌
 */
@AllArgsConstructor
@Getter
public enum PvService {

  @JsonProperty("NicoNicoDouga")
  NICONICO("NicoNicoDouga"),

  @JsonProperty("Youtube")
  YOUTUBE("Youtube"),

  @JsonProperty("Bilibili")
  BILIBILI("Bilibili");

  private final String serviceName;
}
