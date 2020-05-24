package mikufan.cx.common.util.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.eclipsecollections.EclipseCollectionsModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * a util class about object mapper
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjMapperUtil {

  public static ObjectMapper createDefault(){
    return JsonMapper.builder()
        .addModule(new EclipseCollectionsModule())
        .enable(SerializationFeature.INDENT_OUTPUT)
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .build();
  }

  public static ObjectMapper createDefaultForReadOnly(){
    return JsonMapper.builder()
        .addModule(new EclipseCollectionsModule())
        .build();
  }

  public static ObjectMapper addBeautifulWriteSupport(@NonNull ObjectMapper mapper){
    return mapper.enable(SerializationFeature.INDENT_OUTPUT).setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static ObjectMapper addDefaultSort(@NonNull ObjectMapper mapper){
    return mapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
  }
}
