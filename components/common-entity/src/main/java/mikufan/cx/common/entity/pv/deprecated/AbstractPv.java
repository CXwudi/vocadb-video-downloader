package mikufan.cx.common.entity.pv.deprecated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import mikufan.cx.common.entity.pv.VocaDbPv;

import java.util.Comparator;

/**
 * The PV class to simply represent the PV to be download
 * @author CX无敌
 * @deprecated use the new {@link VocaDbPv} class instead
 */
@Getter @ToString @EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "_type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = IdentifiedPv.class, name = "identified pv"),
    @JsonSubTypes.Type(value = PvOld.class, name = "pv")
    //New subclass need to be declared here
})
@JsonPropertyOrder({"name", "pvId", "service"})
@Deprecated(since = "4.0.0")
public abstract class AbstractPv implements Comparable<AbstractPv> {
  // is better to have a protected default constructor for jackson for this class and subclasses
  // the equals() is designed to make sure every single PV is a different instance

  @JsonProperty("pvId")
  protected String pvId;

  @JsonProperty("service")
  protected String service;

  @EqualsAndHashCode.Exclude
  @JsonProperty("name")
  protected String name;

  /**
   * we decide to give a default compare method base on pv id and service
   */
  @Override
  public int compareTo(AbstractPv o) {
    return Comparator
        .comparing(AbstractPv::getService)
        .thenComparing(AbstractPv::getPvId)
        .compare(this, o);
  }
}
