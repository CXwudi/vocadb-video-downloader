package mikufan.cx.project_vd_common_util.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperBuilder;
import com.fasterxml.jackson.datatype.eclipsecollections.EclipseCollectionsModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.NonNull;


abstract class ObjMapperUtil {

  public static <M extends ObjectMapper> M addBeautifulWriteSupport(@NonNull M mapper){
    return (M) mapper.enable(SerializationFeature.INDENT_OUTPUT).setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static <M extends ObjectMapper> M addDefaultSort(@NonNull M mapper){
    return (M) mapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
  }

  protected static <M extends ObjectMapper, B extends MapperBuilder<M,B>> M addDefaultFlagsForReadWrite(B builder){
    return addDefaultFlags(builder)
        .enable(SerializationFeature.INDENT_OUTPUT)
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .build();
  }

  protected static <M extends ObjectMapper, B extends MapperBuilder<M,B>> M addDefaultFlagsForReadOnly(B builder){
    return addDefaultFlags(builder)
        .build();
  }

  protected static <M extends ObjectMapper, B extends MapperBuilder<M,B>> B addDefaultFlags(B builder){
    return builder
        .addModule(new EclipseCollectionsModule())
        .addModule(new ParameterNamesModule());
  }
}
