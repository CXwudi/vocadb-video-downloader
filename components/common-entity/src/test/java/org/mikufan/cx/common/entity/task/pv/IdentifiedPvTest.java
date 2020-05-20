package org.mikufan.cx.common.entity.task.pv;

import org.junit.jupiter.api.Test;
import org.mikufan.cx.common.entity.common.VideoWebsiteString;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test transitive property across different subclasses
 */
class IdentifiedPvTest {

  @Test
  void testEquals() {
    var pv = new Pv("sm123456", VideoWebsiteString.NICONICO, "Nice Deco*27 PV");
    var pvVocadb = new IdentifiedPv("sm123456", VideoWebsiteString.NICONICO, "Nice 40mP PV", 435);
    assertEquals(pv, pvVocadb);
    assertEquals(pvVocadb, pv);
  }

  @Test
  void testEquals2() {
    AbstractPv pv = new Pv("sm123456", VideoWebsiteString.NICONICO, "Nice Deco*27 PV");
    AbstractPv pvVocadb = new IdentifiedPv("sm123456", VideoWebsiteString.NICONICO, "Nice 40mP PV", 435);
    assertEquals(pv, pvVocadb);
    assertEquals(pvVocadb, pv);
  }
}