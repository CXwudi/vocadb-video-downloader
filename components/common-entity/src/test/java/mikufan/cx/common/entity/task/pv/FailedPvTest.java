package mikufan.cx.common.entity.task.pv;

import mikufan.cx.common.entity.common.PvServiceString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FailedPvTest {

  @Test
  void testEquals() {
    IdentifiedPv pv = new IdentifiedPv("sm123456", PvServiceString.NICONICO, "A random しゃべる帽子 PV", 123456);
    FailedPv pv1 = new FailedPv(pv,"deleted pv");
    FailedPv pv2 = new FailedPv(pv,"hided pv");
    assertEquals(pv1,pv2);
  }
}