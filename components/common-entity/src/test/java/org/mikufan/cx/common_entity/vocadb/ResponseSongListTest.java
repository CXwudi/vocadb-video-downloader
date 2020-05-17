package org.mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.eclipsecollections.EclipseCollectionsModule;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ResponseSongListTest {
  private ObjectMapper objectMapper = JsonMapper.builder()
      .addModule(new EclipseCollectionsModule())
      .build();


  @Test
  void canParseModel() throws IOException {
    var jsonFile = new File("src/test/resources/vocadb/songListModelSchema.json");
    var response = objectMapper.readValue(jsonFile,ResponseSongList.class);
    System.out.println(response);
    assertTrue(true);
  }

  @Test
  void canParseSampleResponse() throws IOException {
    var jsonFile = new File("src/test/resources/vocadb/sampleSongListResponse.json");
    var response = objectMapper.readValue(jsonFile,ResponseSongList.class);
    System.out.println(response);
    assertTrue(true);
  }
}