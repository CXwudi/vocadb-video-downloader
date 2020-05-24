package mikufan.cx.common.entity.pv;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mikufan.cx.common.entity.common.PvService;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.junit.jupiter.api.Test;

import static mikufan.cx.common.entity.pv.util.SamplePvsGenerator.generateVocadbPvs;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class PvTest {
  private final Pv pv1 = new Pv("sm35767788", PvService.NICONICO,
      "シン・ノンフィクションガール - れるりりfeat.初音ミク＆GUMI", "255950");
  private final Pv pv1NoId = new Pv("sm35767788", PvService.NICONICO,
      "シン・ノンフィクションガール");
  private final Pv pv2 = new Pv("sm35666758", PvService.NICONICO,
      "初音ミク - アカボシ", "253000");
  private final Pv pv2NoId = new Pv("sm35666758", PvService.NICONICO,
      "初音ミク - アカボシ");
  private final Pv pv3 = new Pv("ERo-sPa1a5g", PvService.YOUTUBE,
      "八王子P × Giga「Gimme×Gimme feat. 初音ミク・鏡音リン」", "246033");
  private final Pv pv3NoId = new Pv("ERo-sPa1a5g", PvService.YOUTUBE,
      "八王子P × Giga「Gimme×Gimme feat. 初音ミク・鏡音リン」");
  private final Pv pv3Alt = new Pv("sm35866152", PvService.NICONICO,
      "【初音ミク×鏡音リン】Gimme×Gimme【八王子P×Giga】", "246033");

  /**
   * {@link Pv#equals(java.lang.Object)} should handles these situation
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
    val pv1copy = new Pv(pv1.getPvId(), pv1.getService(), pv1.getName());
    assertEquals(pv1copy, pv1);
    assertEquals(pv1copy, pv1NoId);
    val pv1WithIdCopy = new Pv(pv1.getPvId(), pv1.getService(), pv1.getName(), pv1.getSongId());
    assertEquals(pv1WithIdCopy, pv1);

  }

  /**
   * {@link Pv#compareTo(Pv)} should return 0 when {@link Pv#equals(java.lang.Object)} return true,
   * also
   */
  @Test
  void compareTo() {
    val pv1copy = new Pv(pv1.getPvId(), pv1.getService(), pv1.getName());
    assertEquals(0, pv1.compareTo(pv1NoId));
    assertEquals(0, pv1.compareTo(pv1copy));
    assertEquals(0, pv3.compareTo(pv3Alt));
  }

  /**
   * make sure {@link Pv#compareTo(Pv)} give us right order
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
    assertThrows(NullPointerException.class, () -> new Pv(null, PvService.BILIBILI, ""));
    assertThrows(NullPointerException.class, () -> new Pv("title", null, ""));
    assertThrows(NullPointerException.class, () -> new Pv(null, PvService.BILIBILI, "", ""));
    assertThrows(NullPointerException.class, () -> new Pv("title", null, "", ""));
  }


  /**
   * should be able to filter out pvs with same songIds in set
   */
  @Test
  void testEqualsAndCompareUsingSet() {
    MutableList<Pv> pvs = generateVocadbPvs();
    log.debug("pvs count = {}", pvs.size());
    var set = pvs.toSortedSet();
    log.debug("set size = {}", set.size());
    assertNotEquals(pvs.size(), set.size());
    assertEquals(20, set.size());
  }

  /**
   * should be sorted base on {@link Pv#getPvId()} and {@link Pv#getService()},
   * or base on {@link Pv#getSongId()}.
   * remembered that {@link Pv} has a special equals method that can compare with other pv
   */
  @Test
  void testCompareUsingSet2(){
    MutableList<Pv> pvs = generateVocadbPvs();
    MutableSortedSet<Pv> set = pvs.collectWithIndex(
        (pv, index) -> index % 2 == 0 ?
            new Pv(pv.getPvId(), pv.getService(), pv.getName()): new Pv(pv.getPvId(), pv.getService(), pv.getName(), String.valueOf(index))
    ).toSortedSet();
    log.debug("pvs count = {}, sorted set size = {}", pvs.size(), set.size());
    assertEquals(pvs.size(), set.size());
  }
}