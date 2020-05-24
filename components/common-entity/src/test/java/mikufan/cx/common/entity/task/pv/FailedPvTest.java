package mikufan.cx.common.entity.task.pv;

import mikufan.cx.common.entity.common.PvService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FailedPvTest {

  @Test
  void testEquals() {
    Pv pv = new Pv("sm123456", PvService.NICONICO, "A random しゃべる帽子 PV", "123456");
    FailedPv pv1 = new FailedPv(pv,"deleted pv");
    FailedPv pv2 = new FailedPv(pv,"hided pv");
    assertEquals(pv1,pv2);
  }
}