package mikufan.cx.common_util.jackson;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * a util class about json object mapper
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonMapperUtil extends ObjMapperUtil {

  public static JsonMapper createDefault(){
    return addDefaultFlags(JsonMapper.builder());
  }

  public static JsonMapper createDefaultForReadOnly(){
    return addDefaultFlagsForReadOnly(JsonMapper.builder());
  }


}
