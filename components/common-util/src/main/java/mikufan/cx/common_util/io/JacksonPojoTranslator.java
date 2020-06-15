package mikufan.cx.common_util.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.common_util.jackson.JsonMapperUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * @author CX无敌
 */
@Getter @Slf4j
@AllArgsConstructor
public class JacksonPojoTranslator<P> implements PojoFileTranslator<P> {

  @NonNull
  private final ObjectMapper mapper;

  public static <T> JacksonPojoTranslator<T> createWithDefaultMapper(){
    return new JacksonPojoTranslator<>(JsonMapperUtil.createDefault());
  }

  @Override
  public Optional<P> read(Path file) {
    try {
      return Optional.of(mapper.readValue(file.toFile(), new TypeReference<P>() {}));
    } catch (IOException e) {
      log.error("java.io.IOException in read", e);
      return Optional.empty();
    }

  }

  @Override
  public boolean write(P pojo, Path dest) {
    try {
      mapper.writeValue(dest.toFile(), pojo);
      return Files.exists(dest);
    } catch (IOException e) {
      log.error("java.io.IOException in write", e);
      return false;
    }
  }
}
