package mikufan.cx.common.entity.pv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import mikufan.cx.common.entity.common.PvService;

/**
 * A flatten class for representing a PV with a VocaDB songId to identify itself.
 * <br\>
 * Learn JAVA: if possible, should never introduce inheritance in json-pojo, unless one of
 * the pojo want to hold cross-types of one abstract type.
 * One of the reason is we don't want json contain type information in json.
 * @author CX无敌
 */
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({"name", "pvId", "service", "songId"})
public class VocaDbPv implements Comparable<VocaDbPv> {
  //a protected default constructor used by jackson only
  /**
   * the website specific id
   */
  @JsonProperty @NonNull
  protected String pvId;

  /**
   * which website does it belongs to
   */
  @JsonProperty @NonNull
  protected PvService service;

  /**
   * name of the pv, can be null
   */
  @JsonProperty
  protected String name;

  /**
   * which song does the pv belongs to
   */
  @JsonProperty
  protected int songId;

  /**
   * simply just compare {@link VocaDbPv#songId}.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof VocaDbPv)) {
      return false;
    }
    VocaDbPv vocaDbPv = (VocaDbPv) o;
    return this.songId == vocaDbPv.songId;
  }

  /**
   * @return {@link VocaDbPv#songId}
   */
  @Override
  public int hashCode() {
    return this.songId;
  }

  /**
   * sorted base on {@link VocaDbPv#songId} <br\>
   * Learn Java: it's hard to do a conditional comparing, so just do a simple sort
   */
  @Override
  public int compareTo(VocaDbPv o) {
    return Integer.compare(this.songId, o.songId);
  }
}
