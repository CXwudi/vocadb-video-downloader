package org.mikufan.cx.common.entity.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.eclipsecollections.EclipseCollectionsModule;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.junit.jupiter.api.Test;
import org.mikufan.cx.common.entity.task.pv.AbstractPv;
import org.mikufan.cx.common.entity.task.pv.IdentifiedPv;
import org.mikufan.cx.common.entity.task.pv.Pv;
import org.mikufan.cx.common.entity.vocadb.ResponseSongList;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PvTaskTest {
  private ObjectMapper objectMapper = JsonMapper.builder()
      .addModule(new EclipseCollectionsModule())
      .enable(SerializationFeature.INDENT_OUTPUT)
//      .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
      .serializationInclusion(JsonInclude.Include.NON_NULL)
      .build();

  private File testJsonFilesPath = new File("src/test/resources/task/");

  /* equals and compareTo test*/

  /**
   * should be able to filter out pvs with same songIds in set
   */
  @Test
  void testEqualsAndCompareUsingSet() {
    MutableList<IdentifiedPv> identifiedPvs = generateVocadbPvs();
    log.debug("identifiedPvs count = {}", identifiedPvs.size());
    var set = identifiedPvs.toSortedSet();
    log.debug("set size = {}", set.size());
    assertNotEquals(identifiedPvs.size(), set.size());
    assertEquals(20, set.size());
  }

  /**
   * should be sorted base on {@link AbstractPv#getPvId()} and {@link AbstractPv#getService()},
   * or base on {@link IdentifiedPv#getSongId()}.
   * remembered that {@link IdentifiedPv} has a special equals method that can compare with other pv
   */
  @Test
  void testCompareUsingSet2(){
    MutableList<IdentifiedPv> identifiedPvs = generateVocadbPvs();
    MutableSortedSet<AbstractPv> pvs = identifiedPvs.collectWithIndex(
        (pv, index) -> index % 2 == 0 ?
            new Pv(pv.getPvId(), pv.getService(), pv.getName()): new IdentifiedPv(pv.getPvId(), pv.getService(), pv.getName(), index)
    ).toSortedSet();
    log.debug("identifiedPvs count = {}, sorted set size = {}", identifiedPvs.size(), pvs.size());
    assertEquals(identifiedPvs.size(), pvs.size());
  }

  /*jackson deserialize/serialize test */

  /**
   * should deserializer pv task obj from model json
   */
  @Test @SneakyThrows
  void testDeserializeJsonModel(){
    var taskModel = objectMapper.readValue(
        new File(testJsonFilesPath,"pvTaskModel.json"),
        new TypeReference<PvTask<AbstractPv>>(){});
    assertEquals(2, taskModel.getDones().size());
  }

  /**
   * should be able to serializer a pv task of {@link IdentifiedPv}
   */
  @Test @SneakyThrows
  void testJsonSerialization() {
    var identifiedPvs = generateVocadbPvs().toSortedSet();
    var task = new PvTask<IdentifiedPv>("2019 Vocaloid Song");
    task.getTodos().addAll(identifiedPvs);
    log.debug("a task is created, folder = {}, todo count = {}", task.getFolderName(), task.getTodos().size());
    var outputFile = new File(testJsonFilesPath, "sampleTask.json");
    outputFile.createNewFile();
    objectMapper.writeValue(outputFile, task);
  }

  /**
   * should be able to serializer a task with multi pv types, although shouldn't exist in production
   */
  @Test @SneakyThrows
  void testJsonSerialization2() {
    MutableSortedSet<AbstractPv> pvs = generateVocadbPvs().collectWithIndex(
        (pv, index) -> index % 2 == 0 ?
            new Pv(pv.getPvId(), pv.getService(), pv.getName()): new IdentifiedPv(pv.getPvId(), pv.getService(), pv.getName(), index)
    ).toSortedSet();
    log.debug("pvs.size = {}", pvs.size());
    var task = new PvTask<>("2020 Vocaloid Song");
    task.getTodos().addAll(pvs);
    log.info("a task is created, folder = {}, todo count = {}", task.getFolderName(), task.getTodos().size());
    var outputFile = new File(testJsonFilesPath, "sampleTask2.json");
    outputFile.createNewFile();
    objectMapper.writeValue(outputFile, task);
  }

  /**
   * should be able to deserializer a task, given the generic type of pvs
   */
  @Test @SneakyThrows
  void testJsonDeserialization(){
    var jsonFile = new File(testJsonFilesPath, "sampleTask.json");
    var jsonFile2 = new File(testJsonFilesPath, "sampleTask2.json");
    
    var task = objectMapper.readValue(jsonFile, new TypeReference<PvTask<IdentifiedPv>>(){});
    log.debug("task = {}", task);
    
    var task2 = objectMapper.readValue(jsonFile2, new TypeReference<PvTask<AbstractPv>>() {});
    log.debug("task2 = {}", task2);
    log.debug("task size = {}, task2 size = {}", task.getTodos().size(), task2.getTodos().size());
    assertTrue(true);
  }

  /** this generate multiple IdentifiedPv with repeated songIds */
  private MutableList<IdentifiedPv> generateVocadbPvs() {
    var file = new File("src/test/resources/vocadb/songListNeededResponse.json");
    ResponseSongList response = null;
    try {
      response = objectMapper.readValue(file, ResponseSongList.class);
    } catch (IOException e) {
      fail(e);
    }
    assertNotNull(response);
    var pvs = response.getItems().toList().flatCollect(
        item ->
          item.getSong().getPvs().collect(pvItem ->
              new IdentifiedPv(pvItem.getPvId(), pvItem.getService(), pvItem.getName(), item.getSong().getId())
          )

    );
    return pvs;
  }

}
