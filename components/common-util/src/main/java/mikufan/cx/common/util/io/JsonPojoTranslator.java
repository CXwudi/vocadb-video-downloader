package mikufan.cx.common.util.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common.util.jackson.ObjMapperUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/**
 * @author CX无敌
 */
@Getter @Slf4j
@AllArgsConstructor
public class JsonPojoTranslator<P> implements PojoFileTranslator<P> {

  private final ObjectMapper mapper;

  public static <T> JsonPojoTranslator<T> createWithDefaultMapper(){
    return new JsonPojoTranslator<>(ObjMapperUtil.createDefault());
  }

  @Override
  public Optional<P> read(File file) {
    try {
      return Optional.of(mapper.readValue(file, new TypeReference<P>() {}));
    } catch (IOException e) {
      log.error("java.io.IOException in read", e);
      return Optional.empty();
    }

  }

  @Override
  public boolean write(P pojo, File dest) {
    try {
      mapper.writeValue(dest, pojo);
      return Files.exists(dest.toPath());
    } catch (IOException e) {
      log.error("java.io.IOException in write", e);
      return false;
    }
  }
}
