package mikufan.cx.common_entity.pv;

import mikufan.cx.common_entity.common.PvService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FailedVocaDbPvTest {

  @Test
  void testEquals() {
    VocaDbPv vocaDbPv = new VocaDbPv("sm123456", PvService.NICONICO, "A random しゃべる帽子 PV", 123456);
    FailedPv pv1 = new FailedPv(vocaDbPv,"deleted vocaDbPv");
    FailedPv pv2 = new FailedPv(vocaDbPv,"hided vocaDbPv");
    assertEquals(pv1,pv2);
  }
}