package mikufan.cx.common_entity.pv;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import mikufan.cx.common_entity.common.PvService;
import mikufan.cx.common_entity.pv.util.SamplePvsGenerator;
import org.eclipse.collections.api.list.MutableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class VocaDbPvTest {
  private final VocaDbPv vocaDbPv1 = new VocaDbPv("sm35767788", PvService.NICONICO,
      "シン・ノンフィクションガール - れるりりfeat.初音ミク＆GUMI", 255950);
  private final VocaDbPv vocaDbPv2 = new VocaDbPv("sm35666758", PvService.NICONICO,
      "初音ミク - アカボシ", 253000);
  private final VocaDbPv vocaDbPv3 = new VocaDbPv("ERo-sPa1a5g", PvService.YOUTUBE,
      "八王子P × Giga「Gimme×Gimme feat. 初音ミク・鏡音リン」", 246033);
  private final VocaDbPv vocaDbPv3Alt = new VocaDbPv("sm35866152", PvService.NICONICO,
      "【初音ミク×鏡音リン】Gimme×Gimme【八王子P×Giga】", 246033);

  /**
   * {@link VocaDbPv#equals(java.lang.Object)} should handles these situation
   */
  @Test
  void testEquals() {
    assertEquals(vocaDbPv3, vocaDbPv3Alt); //tell equals base on songId, even from two different pvs
    assertNotEquals(vocaDbPv1, vocaDbPv2); //tell difference base on songId
  }

  /**
   * new copies should be equal
   */
  @Test
  void testIdentity(){
    val pv1copy = new VocaDbPv(vocaDbPv1.getPvId(), vocaDbPv1.getService(), vocaDbPv1.getName(), vocaDbPv1.getSongId());
    assertEquals(pv1copy, vocaDbPv1);

  }

  /**
   * {@link VocaDbPv#compareTo(VocaDbPv)} should return 0 when {@link VocaDbPv#equals(java.lang.Object)} return true,
   * also
   */
  @Test
  void compareTo() {
    val pv1copy = new VocaDbPv(vocaDbPv1.getPvId(), vocaDbPv1.getService(), vocaDbPv1.getName(), vocaDbPv1.getSongId());
    assertEquals(0, vocaDbPv1.compareTo(pv1copy));
    assertEquals(0, vocaDbPv3.compareTo(vocaDbPv3Alt));
  }

  /**
   * make sure {@link VocaDbPv#compareTo(VocaDbPv)} give us right order
   */
  @Test
  void compareToOrder() {
    assertTrue(vocaDbPv1.compareTo(vocaDbPv2) > 0);
    assertTrue(vocaDbPv3.compareTo(vocaDbPv1) < 0);
  }

  /**
   * should not allow creating pv with null on non-null fields
   */
  @Test
  void notNull(){
    assertThrows(NullPointerException.class, () -> new VocaDbPv(null, PvService.BILIBILI, "", -1));
    assertThrows(NullPointerException.class, () -> new VocaDbPv("title", null, "", -1));
  }


  /**
   * should be able to filter out pvs with same songIds in set
   */
  @Test
  void testEqualsUsingSet() {
    MutableList<VocaDbPv> vocaDbPvs = SamplePvsGenerator.generateVocadbPvs();
    log.debug("vocaDbPvs count = {}", vocaDbPvs.size());
    var set = vocaDbPvs.toSet();
    log.debug("set size = {}", set.size());
    assertNotEquals(vocaDbPvs.size(), set.size());
    assertEquals(20, set.size());
  }


  /**
   * should be able to filter out pvs with same songIds in sorted set
   */
  @Test
  void testCompareUsingSet() {
    MutableList<VocaDbPv> vocaDbPvs = SamplePvsGenerator.generateVocadbPvs();
    log.debug("vocaDbPvs count = {}", vocaDbPvs.size());
    var set = vocaDbPvs.toSortedSet();
    log.debug("sorted set size = {}", set.size());
    assertNotEquals(vocaDbPvs.size(), set.size());
    assertEquals(20, set.size());
  }
}