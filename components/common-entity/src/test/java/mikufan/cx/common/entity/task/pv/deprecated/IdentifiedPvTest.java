package mikufan.cx.common.entity.task.pv.deprecated;

import mikufan.cx.common.entity.common.PvService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test transitive property across different subclasses
 */
@Disabled("test case of deprecated class")
class IdentifiedPvTest {

  @Test
  void testEquals() {
    var pv = new PvOld("sm123456", PvService.NICONICO.getServiceName(), "Nice Deco*27 PV");
    var pvVocadb = new IdentifiedPv("sm123456", PvService.NICONICO.getServiceName(), "Nice 40mP PV", 435);
    assertEquals(pv, pvVocadb);
    assertEquals(pvVocadb, pv);
    assertEquals(0, pv.compareTo(pvVocadb));
    assertEquals(0, pvVocadb.compareTo(pv));

  }

  @Test
  void testEquals2() {
    AbstractPv pv = new PvOld("sm123456", PvService.NICONICO.getServiceName(), "Nice Deco*27 PV");
    AbstractPv pvVocadb = new IdentifiedPv("sm123456", PvService.NICONICO.getServiceName(), "Nice 40mP PV", 435);
    assertEquals(pv, pvVocadb);
    assertEquals(pvVocadb, pv);
    assertEquals(0, pv.compareTo(pvVocadb));
    assertEquals(0, pvVocadb.compareTo(pv));
  }
}