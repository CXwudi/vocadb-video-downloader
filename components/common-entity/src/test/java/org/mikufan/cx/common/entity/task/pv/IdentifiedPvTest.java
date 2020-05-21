package org.mikufan.cx.common.entity.task.pv;

import org.junit.jupiter.api.Test;
import org.mikufan.cx.common.entity.common.PvServiceString;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test transitive property across different subclasses
 */
class IdentifiedPvTest {

  @Test
  void testEquals() {
    var pv = new Pv("sm123456", PvServiceString.NICONICO, "Nice Deco*27 PV");
    var pvVocadb = new IdentifiedPv("sm123456", PvServiceString.NICONICO, "Nice 40mP PV", 435);
    assertEquals(pv, pvVocadb);
    assertEquals(pvVocadb, pv);
    assertEquals(0, pv.compareTo(pvVocadb));
    assertEquals(0, pvVocadb.compareTo(pv));

  }

  @Test
  void testEquals2() {
    AbstractPv pv = new Pv("sm123456", PvServiceString.NICONICO, "Nice Deco*27 PV");
    AbstractPv pvVocadb = new IdentifiedPv("sm123456", PvServiceString.NICONICO, "Nice 40mP PV", 435);
    assertEquals(pv, pvVocadb);
    assertEquals(pvVocadb, pv);
    assertEquals(0, pv.compareTo(pvVocadb));
    assertEquals(0, pvVocadb.compareTo(pv));
  }
}