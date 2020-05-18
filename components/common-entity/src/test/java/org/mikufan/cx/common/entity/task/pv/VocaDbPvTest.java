package org.mikufan.cx.common.entity.task.pv;

import org.junit.jupiter.api.Test;
import org.mikufan.cx.common.entity.common.VideoWebsiteString;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VocaDbPvTest {

  @Test
  void testEquals() {
    var pv = new Pv("sm123456", VideoWebsiteString.NICONICO, "Nice Deco*27 PV");
    var pvVocadb = new VocaDbPv("sm123456", VideoWebsiteString.NICONICO, "Nice Deco*27 PV", 435);
    assertEquals(pv, pvVocadb);
    assertEquals(pvVocadb, pv);
  }


}