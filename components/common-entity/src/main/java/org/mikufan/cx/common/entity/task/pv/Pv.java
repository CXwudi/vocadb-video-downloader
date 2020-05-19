package org.mikufan.cx.common.entity.task.pv;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The simply implementation of {@link AbstractPv}
 * @author CX无敌
 */
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pv extends AbstractPv { //need a protected default constructor for jackson

  public Pv(String pvId, String service, String name) {
    super(pvId, service, name);
  }
}
