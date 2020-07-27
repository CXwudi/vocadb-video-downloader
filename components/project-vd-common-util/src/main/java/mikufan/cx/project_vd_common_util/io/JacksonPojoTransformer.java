package mikufan.cx.project_vd_common_util.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mikufan.cx.project_vd_common_util.jackson.JsonMapperUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * transforming between files and POJOs using Jackson
 * @author CX无敌
 */
@Getter @Slf4j
@AllArgsConstructor
public class JacksonPojoTransformer<P> implements PojoFileTransformer<P> {

  @NonNull
  private final ObjectMapper mapper;

  /**
   * finally we decide to store the class<p>
   * TypeReference is very fragile. thanks for the stupid Java type erasing.
   */
  @NonNull
  private final Class<P> tClass;

  /**
   * create it using the {@link JsonMapperUtil#createDefault()}
   * @param tClass class obj of the POJO
   */
  public static <T> JacksonPojoTransformer<T> createWithDefaultMapper(Class<T> tClass){
    return new JacksonPojoTransformer<>(JsonMapperUtil.createDefault(), tClass);
  }

  /**
   * read the file and translate it to POJO <p>
   * can return null if the file contains a null
   * @param file the file to read
   * @return correspond POJO
   * @throws IOException if exception thrown from read
   */
  @Override
  public P read(Path file) throws IOException {
    try {
      return mapper.readValue(file.toFile(), tClass);
    } catch (IOException e) {
      log.error("java.io.IOException in read", e);
      throw e;
    }
  }

  /**
   * write the POJO back to the file
   * @param pojo the pojo to write
   * @param dest the file where to write the pojo
   * @return {@code true} if the file exists after write
   * @throws IOException if exception thrown from write
   */
  @Override
  public boolean write(P pojo, Path dest) throws IOException {
    try {
      mapper.writeValue(dest.toFile(), pojo);
      return Files.exists(dest);
    } catch (IOException e) {
      log.error("java.io.IOException in write", e);
      throw e;
    }
  }
}
