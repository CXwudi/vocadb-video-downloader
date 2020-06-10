package mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.eclipsecollections.EclipseCollectionsModule;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.factory.Lists;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ResponseSongListTest {
  private final ObjectMapper objectMapper = JsonMapper.builder()
      .addModule(new EclipseCollectionsModule())
      .build();
  private final String parent = "src/test/resources/vocadb";


  /**
   * should parse the json model
   */
  @Test @SneakyThrows
  void testParseModel() {
    var jsonFile = new File(parent, "songListModelSchema.json");
    var response = objectMapper.readValue(jsonFile, ResponseSongList.class);
    log.info("{}", response);
    assertTrue(true);
  }

  /**
   * should be able to parse the response with full details
   */
  @Test
  void testParseSampleResponse() {
    var samples = Lists.immutable.of(
        "songListFullResponseSample1.json",
        "songListFullResponseSample2.json"
    );
    samples.forEach(json -> {
      var jsonFile = new File(parent, json);
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

  /**
   * should be able to merge two responses into one without duplicating songs
   */
  @Test
  void testEqualsAndHashCode(){
    var responses = Lists.immutable.of(
        "songListFullResponseSample1.json",
        "songListFullResponseSample2.json"
    ).collect(str -> {
      var file = new File(parent, str);
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
          var set = res1.getItems().toSortedSet();
          set.addAll(res2.getItems());
          res1.getItems().clear();
          res1.getItems().addAll(set);
          return res1;
        });

    log.info("{}", mergedResponse);
    assertEquals(8, mergedResponse.getItems().size());

  }

  @Test @SneakyThrows
  void testWriteBackToFile(){
    var jsonFile = new File(parent, "songListModelSchema.json");
    var response = objectMapper.readValue(jsonFile,ResponseSongList.class);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT).enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
    var resultFile = new File(parent, "songListModelSchemaRewritten.json");
    resultFile.deleteOnExit();
    objectMapper.writeValue(resultFile, response);
    assertTrue(true);
  }


}