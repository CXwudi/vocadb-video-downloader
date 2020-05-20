package org.mikufan.cx.common.entity.task.pv;

import org.junit.jupiter.api.Test;
import org.mikufan.cx.common.entity.common.VideoWebsiteString;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FailedPvTest {

  @Test
  void testEquals() {
    IdentifiedPv pv = new IdentifiedPv("sm123456", VideoWebsiteString.NICONICO, "A random しゃべる帽子 PV", 123456);
    FailedPv pv1 = new FailedPv(pv,"deleted pv");
    FailedPv pv2 = new FailedPv(pv,"hided pv");
    assertEquals(pv1,pv2);
  }
}