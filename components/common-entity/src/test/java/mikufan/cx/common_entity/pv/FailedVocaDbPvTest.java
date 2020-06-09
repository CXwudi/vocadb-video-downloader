package mikufan.cx.common_entity.pv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FailedVocaDbPvTest {

  @Test
  void testEquals() {
    VocaDbPv vocaDbPv = new VocaDbPv("sm123456", PvService.NICONICO, "A random しゃべる帽子 PV", 123456);
    FailedVocaDbPv pv1 = new FailedVocaDbPv(vocaDbPv,"deleted vocaDbPv");
    FailedVocaDbPv pv2 = new FailedVocaDbPv(vocaDbPv,"hided vocaDbPv");
    assertEquals(pv1,pv2);
  }

  /**
   * should not throw when sorting null
   */
  @Test
  void testCompareNotThrow(){
    VocaDbPv vocaDbPv = new VocaDbPv("sm123456", PvService.NICONICO, "A random しゃべる帽子 PV", 123456);
    FailedVocaDbPv pv1 = new FailedVocaDbPv(vocaDbPv,"deleted vocaDbPv");
    FailedVocaDbPv pv2 = null;
    assertDoesNotThrow(() -> pv1.compareTo(pv2));
  }

  /**
   * should not create without a vocadb pv
   */
  @Test
  void testNonNull(){
    assertThrows(NullPointerException.class, () -> new FailedVocaDbPv(null,"deleted vocaDbPv"));
  }
}