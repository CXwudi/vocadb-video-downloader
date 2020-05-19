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
import org.junit.jupiter.api.Test;
import org.mikufan.cx.common.entity.task.pv.AbstractPv;
import org.mikufan.cx.common.entity.task.pv.Pv;
import org.mikufan.cx.common.entity.task.pv.VocaDbPv;
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

  @Test
  void testEqualsUsingSet() {
    MutableList<VocaDbPv> vocaDbPvs = generateVocadbPvs();
    log.debug("vocaDbPvs count = {}", vocaDbPvs.size());
    var set = vocaDbPvs.toSet();
    log.debug("set size = {}", set.size());
    assertNotEquals(vocaDbPvs.size(), set.size());
  }

  @Test @SneakyThrows
  void testJsonSerialization() {
    var vocaDbPvs = generateVocadbPvs();
    var task = new PvTask<VocaDbPv>("2019 Vocaloid Song");
    task.getTodos().addAll(vocaDbPvs);
    log.debug("a task is created, folder = {}, todo count = {}", task.getFolderName(), task.getTodos().size());
    var outputFile = new File("src/test/resources/task/sampleTask.json");
    outputFile.createNewFile();
    objectMapper.writeValue(outputFile, task);
  }

  /**
   * Multi pv types mix tasks, although shouldn't exist in production
   */
  @Test @SneakyThrows
  void testJsonSerialization2() {
    MutableList<AbstractPv> pvs = generateVocadbPvs().collectWithIndex(
        (pv, index) -> index % 2 == 0 ? new Pv(pv.getPvId(), pv.getService(), pv.getName()): pv
    );
    log.debug("pvs.size = {}", pvs.size());
    var task = new PvTask<>("2020 Vocaloid Song");
    task.getTodos().addAll(pvs);  //FIXME: something doesn't get added here
    log.info("a task is created, folder = {}, todo count = {}", task.getFolderName(), task.getTodos().size());
    var outputFile = new File("src/test/resources/task/sampleTask2.json");
    outputFile.createNewFile();
    objectMapper.writeValue(outputFile, task);
  }

  @Test @SneakyThrows
  void testJsonDeserialization(){
    var jsonFile = new File("src/test/resources/task/sampleTask.json");
    var jsonFile2 = new File("src/test/resources/task/sampleTask2.json");
    
    var task = objectMapper.readValue(jsonFile, new TypeReference<PvTask<VocaDbPv>>(){});
    log.debug("task = {}", task);
    
    var task2 = objectMapper.readValue(jsonFile2, new TypeReference<PvTask<AbstractPv>>() {});
    log.debug("task2 = {}", task2);
    log.debug("task size = {}, task2 size = {}", task.getTodos().size(), task2.getTodos().size());

    var originaList = generateVocadbPvs();
    var rest = originaList.toList();
    originaList.forEach(pv -> {
      if (!task.getTodos().contains(pv)){
        log.debug("task2 doesn't contain = {}", pv);
      }
    });
  }

  /** this can generate VocaDbPv with same songIds */
  private MutableList<VocaDbPv> generateVocadbPvs() {
    var file = new File("src/test/resources/vocadb/songListPvs.json");
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
              new VocaDbPv(pvItem.getPvId(), pvItem.getService(), pvItem.getName(), item.getSong().getId())
          )

    );
    return pvs;
  }

}
