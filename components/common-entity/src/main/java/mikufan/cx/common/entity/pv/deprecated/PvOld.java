package mikufan.cx.common.entity.pv.deprecated;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The simply implementation of {@link AbstractPv}
 * @author CX无敌
 * @deprecated use the new {@link mikufan.cx.common.entity.pv.Pv} class instead
 */
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Deprecated(since = "4.0.0")
public class PvOld extends AbstractPv { //need a protected default constructor for jackson

  public PvOld(String pvId, String service, String name) {
    super(pvId, service, name);
  }
}
