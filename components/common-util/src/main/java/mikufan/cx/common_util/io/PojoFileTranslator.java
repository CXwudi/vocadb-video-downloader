package mikufan.cx.common_util.io;

import java.nio.file.Path;
import java.util.Optional;

/**
 *
 * @param <P>
 * @author CX无敌
 */
public interface PojoFileTranslator<P> {

  /**
   * read a file and return the content in pojo
   * @param file the file to read
   * @return the pojo wrapped in Option if read success, else {@link Optional#empty()}
   */
  Optional<P> read(Path file);

  /**
   * write a pojo back to a file
   * @param pojo the pojo to write
   * @param dest the file where to write the pojo
   * @return {@code true} if success
   */
  boolean write(P pojo, Path dest);

}
