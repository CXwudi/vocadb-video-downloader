package org.mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.eclipsecollections.EclipseCollectionsModule;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.factory.Lists;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ResponseSongListTest {
  private ObjectMapper objectMapper = JsonMapper.builder()
      .addModule(new EclipseCollectionsModule())
      .build();


  @Test
  void testParseModel() throws IOException {
    var jsonFile = new File("src/test/resources/vocadb/songListModelSchema.json");
    var response = objectMapper.readValue(jsonFile,ResponseSongList.class);
    log.info("{}", response);
    assertTrue(true);
  }

  @Test
  void testParseSampleResponse() {
    var samples = Lists.immutable.of(
        "songListResponseSample1.json",
        "songListResponseSample2.json"
    );
    samples.forEach(json -> {
      var jsonFile = new File("src/test/resources/vocadb", json);
      ResponseSongList response = null;
      try {
        response = objectMapper.readValue(jsonFile, ResponseSongList.class);
      } catch (IOException e) {
        fail(e);
      }
      log.info("{}", response);
      assertNotNull(response);
    });
  }

  @Test
  void testEqualsAndHashCode(){
    var responses = Lists.immutable.of(
        "songListResponseSample1.json",
        "songListResponseSample2.json"
    ).collect(str -> {
      var file = new File("src/test/resources/vocadb", str);
      ResponseSongList response = null;
      try {
        response = objectMapper.readValue(file, ResponseSongList.class);
      } catch (IOException e) {
        fail(e);
      }
      assertNotNull(response);
      return response;
    });

    var mergedResponse = responses.drop(1).injectInto(
        responses.get(0),
        (res1, res2) ->{
          res1.getItems().addAll(res2.getItems());
          return res1;
        });

    log.info("{}", mergedResponse);
    assertEquals(8, mergedResponse.getItems().size());

  }


}