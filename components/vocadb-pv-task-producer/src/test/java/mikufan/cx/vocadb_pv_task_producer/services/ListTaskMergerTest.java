package mikufan.cx.vocadb_pv_task_producer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_vocaloid_entity.vocadb.api.songList.get_listid_songs.PartialSongList;
import mikufan.cx.project_vd_common_util.jackson.JsonMapperUtil;
import mikufan.cx.vocadb_pv_task_producer.legacy.v1.main.ListTaskMerger;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class ListTaskMergerTest {
  private String baseDir = "src/test/resources/main";
  private ObjectMapper mapper = JsonMapperUtil.createDefault();
  private Path file1 = Paths.get(baseDir, "songListFullResponseSample1.json");
  private Path file2 = Paths.get(baseDir, "songListFullResponseSample2.json");
  /**
   * a real fixed json data set containing lots of data
   */
  private Path file3 = Paths.get(baseDir, "2019 Vocaloid list data.json");

  private ListTaskMerger merger = new ListTaskMerger();

  @Test @SneakyThrows
  void mergeToList() {
    var songs1 = mapper.readValue(file1.toFile(), PartialSongList.class);
    var songs2 = mapper.readValue(file2.toFile(), PartialSongList.class);
    var songs3 = merger.mergeToList(songs1, songs2);
    log.debug("songs3 = \n{}", mapper.writeValueAsString(songs3));
    assertEquals(8, songs3.getTotalCount());
    assertEquals(8, songs3.getItems().size());
  }

  @Test @SneakyThrows
  void mergeToEmptyList() {
    var songs1 = mapper.readValue(file1.toFile(), PartialSongList.class);
    var songs3 = merger.mergeToList(null, songs1);
    assertEquals(songs1, songs3); // same ref
  }
//
//  @Test @SneakyThrows
//  void mergeToTask() {
//    var listName = "Miku Expo partial list";
//    var tempTask = new VocaDbPvTask(listName);
//    tempTask.markTodo(new VocaDbPv("123456", PvServiceStr.BILIBILI, "Some Bilibili vsong", 123));
//    tempTask.markTodo(new VocaDbPv("yeGtANAcED8", PvServiceStr.YOUTUBE, "「supercell」 Hatsune Miku - ワールドイズマイン (HQ and Lyrics)", 1326));
//    tempTask.markDone(new VocaDbPv("sm123456", PvServiceStr.NICONICO, "Some niconico vsong", 456));
//    tempTask.markDone(new VocaDbPv("sm24536934", PvServiceStr.NICONICO, "【初音ミク】 ヒビカセ 【オリジナル】", 63276));
//    tempTask.markError("Some Empty PV song", "no PV");
//    // this error should be removed when appending song list to task
//    tempTask.markError(new VocaDbPv("smXXXX", PvServiceStr.NICONICO, "『テオ』 / 初音ミク", 161199), "False error");
//    var songs1 = mapper.readValue(file1.toFile(), PartialSongList.class);
//    var newTask = merger.mergeToTask(tempTask, songs1, "Another task name",
//        PvServices.getServices().select(SupportedPvServices::contains));
//    log.debug("newTask = {}", mapper.writeValueAsString(newTask));
//    assertEquals(5, newTask.getTodo().size());
//    assertEquals(2, newTask.getDone().size());
//    assertEquals(1, newTask.getFails().size());
//    assertEquals(listName, newTask.getFolderName());
//
//  }
//
//  /**
//   * able to sort services without break by unsupport services
//   */
//  @Test @SneakyThrows
//  void canSortPvPref() {
//    var songs2019 = mapper.readValue(file3.toFile(), PartialSongList.class);
//    var myPvPref = PvServices.getServices().select(SupportedPvServices::contains);
//    log.debug("myPvPref = {}", myPvPref);
//    var newTask = merger.mergeToTask(null, songs2019, "Another task name", myPvPref);
//    log.debug("newTask = {}", mapper.writeValueAsString(newTask));
//    assertTrue(true);
//  }
}