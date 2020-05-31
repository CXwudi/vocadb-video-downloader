package mikufan.cx.common_entity.pv.deprecated;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mikufan.cx.common_entity.pv.VocaDbPv;

import java.util.Comparator;
import java.util.Objects;


/**
 * The simply implementation of {@link AbstractPv}
 * @author CX无敌
 * @deprecated use the new {@link VocaDbPv} class instead
 */
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Deprecated(since = "4.0.0")
public class PvOld extends AbstractPv implements Comparable<PvOld>{ //need a protected default constructor for jackson

  public PvOld(String pvId, String service, String name) {
    super(pvId, service, name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractPv)) {
      return false;
    }
    AbstractPv that = (AbstractPv) o;
    return Objects.equals(pvId, that.pvId) &&
        Objects.equals(service, that.service);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pvId, service);
  }

  /**
   * we decide to give a default compare method base on pv id and service
   */
  @Override
  public int compareTo(PvOld o) {
    return Comparator
        .comparing(AbstractPv::getService)
        .thenComparing(AbstractPv::getPvId)
        .compare(this, o);
  }
}
