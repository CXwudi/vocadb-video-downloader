package mikufan.cx.common.entity.task.pv;

import lombok.val;
import mikufan.cx.common.entity.common.PvService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Pv2Test {
  private final Pv2 pv1 = new Pv2("sm35767788", PvService.NICONICO,
      "シン・ノンフィクションガール - れるりりfeat.初音ミク＆GUMI", "255950");
  private final Pv2 pv1NoId = new Pv2("sm35767788", PvService.NICONICO,
      "シン・ノンフィクションガール");
  private final Pv2 pv2 = new Pv2("sm35666758", PvService.NICONICO,
      "初音ミク - アカボシ", "253000");
  private final Pv2 pv2NoId = new Pv2("sm35666758", PvService.NICONICO,
      "初音ミク - アカボシ");
  private final Pv2 pv3 = new Pv2("ERo-sPa1a5g", PvService.YOUTUBE,
      "八王子P × Giga「Gimme×Gimme feat. 初音ミク・鏡音リン」", "246033");
  private final Pv2 pv3NoId = new Pv2("ERo-sPa1a5g", PvService.YOUTUBE,
      "八王子P × Giga「Gimme×Gimme feat. 初音ミク・鏡音リン」");
  private final Pv2 pv3Alt = new Pv2("sm35866152", PvService.NICONICO,
      "【初音ミク×鏡音リン】Gimme×Gimme【八王子P×Giga】", "246033");

  /**
   * {@link Pv2#equals(java.lang.Object)} should handles these situation
   */
  @Test
  void testEquals() {
    assertEquals(pv1, pv1NoId);
    assertEquals(pv3, pv3Alt); //tell equals base on songId, even from two different pvs
    assertNotEquals(pv1, pv2); //tell difference base on songId
    assertNotEquals(pv1NoId, pv2NoId); //tell difference base on video id
    assertNotEquals(pv1NoId, pv3NoId); //tell difference base on service
  }

  /**
   * new copies should be equal
   */
  @Test
  void testIdentity(){
    val pv1copy = new Pv2(pv1.getPvId(), pv1.getService(), pv1.getName());
    assertEquals(pv1copy, pv1);
    assertEquals(pv1copy, pv1NoId);
    val pv1WithIdCopy = new Pv2(pv1.getPvId(), pv1.getService(), pv1.getName(), pv1.getSongId());
    assertEquals(pv1WithIdCopy, pv1);

  }

  /**
   * {@link Pv2#compareTo(Pv2)} should return 0 when {@link Pv2#equals(java.lang.Object)} return true,
   * also
   */
  @Test
  void compareTo() {
    val pv1copy = new Pv2(pv1.getPvId(), pv1.getService(), pv1.getName());
    assertEquals(0, pv1.compareTo(pv1NoId));
    assertEquals(0, pv1.compareTo(pv1copy));
    assertEquals(0, pv3.compareTo(pv3Alt));
  }

  /**
   * make sure {@link Pv2#compareTo(Pv2)} give us right order
   */
  @Test
  void compareToOrder() {
    assertTrue(pv1.compareTo(pv3) > 0);         // songId
    assertTrue(pv1NoId.compareTo(pv2NoId) > 0); // pvId
    assertTrue(pv1NoId.compareTo(pv3NoId) < 0); // Niconico < Youtube
  }

  /**
   * should not allow creating pv with null on non-null fields
   */
  @Test
  void notNull(){
    assertThrows(NullPointerException.class, () -> new Pv2(null, PvService.BILIBILI, ""));
    assertThrows(NullPointerException.class, () -> new Pv2("title", null, ""));
    assertThrows(NullPointerException.class, () -> new Pv2(null, PvService.BILIBILI, "", ""));
    assertThrows(NullPointerException.class, () -> new Pv2("title", null, "", ""));
  }
}