package mikufan.cx.vocadb_pv_task_producer.main;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.vocadb.models.ArtistForSongContract;
import mikufan.cx.common_vocaloid_entity.vocadb.models.SongForApiContract;
import mikufan.cx.project_vd_common_util.jackson.JsonMapperUtil;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ArtistFieldFixerTest extends WithHttpClientSupport {
  private final ImmutableList<String> testStrs = Lists.immutable.of(
      "Various artists", "GYARI feat. 鏡音リン V4X (Unknown), 鏡音レン V4X (Unknown)",
      "八王子P, ギガP feat. various", "OSTER project feat. various",
      "100回嘔吐 feat. 初音ミク, 音街ウナ (Unknown), v flower",
      "れるりり feat. Fukase (Unknown)");
  private final ArtistFieldFixer fixer = new ArtistFieldFixer();
  private final ObjectMapper mapper = JsonMapperUtil.createDefault();
  private final String base = "src/test/resources/main/";

  /**
   * can detect any various and unknown
   */
  @Test @SneakyThrows
  void containAmbiguousStrings() {
    testStrs.forEach(s -> assertTrue(fixer.containAmbiguousStrings(s)));
  }

  /**
   * can get the artists by id
   */
  @Test @SneakyThrows
  @Disabled
  void getArtists() {
    // OSTER project feat. various - 軋んだ夢と糸繰人形
    var artists = fixer.getArtists(259123, testedHttpClient);
    log.info("artists = \n{}", JsonMapperUtil.createDefault().writeValueAsString(artists));
  }

  /**
   * can form artist string from artists list
   */
  @Test @SneakyThrows
  void formArtistInfo() {
    var artists = mapper.readValue(new File(base, "artists.json"), // actual artist info from 軋んだ夢と糸繰人形
        new TypeReference<ImmutableList<ArtistForSongContract>>() {});
    var artistStr = fixer.formArtistInfo(artists.toList());
    log.info("artistStr = {}", artistStr);
    assertFalse(fixer.containAmbiguousStrings(artistStr), "fail to combine proper artist str: " + artistStr);
  }

  /**
   * can easily remove unknown
   */
  @Test @SneakyThrows
  void removeUnknown() {
    var selectedStrs = testStrs.select(str -> fixer.containsWithoutCase(str, "Unknown"));
    selectedStrs.forEach(str -> {
      var fixedStr = fixer.removeUnknown(str);
      log.info("str = {}", fixedStr);
      assertFalse(fixer.containsWithoutCase(fixedStr, ArtistFieldFixer.UNKNOWN));
    });
  }

  /**
   * can preserve song info while changing the artist str and artists list
   */
  @Test @SneakyThrows
  void combineNewInfo() {
    var song = mapper.readValue(new File(base, "sampleSongWithUnknownArtist.json"), SongForApiContract.class);
    var fixed = fixer.combineNewInfo(song, "Great Artist", Lists.mutable.of(ArtistForSongContract.builder()
        .categories("Vocaloid").build()));
    mapper.writeValue(new File(base, "sampleSongWithFixedArtist.json"), fixed);
    assertNotNull(fixed.getArtists());
    assertTrue(StringUtils.isNotBlank(fixed.getArtistString()));
  }

  /**
   * it: can fix a song
   */
  @Test @SneakyThrows
  @Disabled
  void fixArtist() {
    // cisco, たかぴぃ, ねじ式 feat. 結月ゆかり, 紲星あかり - ゆづきず☆ダンスナイト
    var smallSong = SongForApiContract.builder().id(272694)
        .name("ゆづきず☆ダンスナイト").artistString("Various artists")
        .songType("Origin").build(); //with manual added "various artists" and other field for testing propose
    var fixed = fixer.fixOne(smallSong, testedHttpClient);
    log.info("fixed = {}", mapper.writeValueAsString(fixed));
    assertFalse(fixer.containAmbiguousStrings(fixed.getArtistString()), "fail to fix the artist string: " + fixed.getArtistString());
    assertEquals("Origin", fixed.getSongType(), "fail to preserve fields in origin song object");
  }
}