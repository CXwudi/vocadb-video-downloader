package mikufan.cx.common.util.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.eclipsecollections.EclipseCollectionsModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ObjMapperBuilder {

  public static ObjectMapper createDefault(){
    return JsonMapper.builder()
        .addModule(new EclipseCollectionsModule())
        .enable(SerializationFeature.INDENT_OUTPUT)
//      .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .build();
  }
}
