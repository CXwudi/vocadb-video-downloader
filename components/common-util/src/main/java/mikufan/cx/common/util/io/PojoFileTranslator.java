package mikufan.cx.common.util.io;

import java.io.File;
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
   * @return the pojo
   */
  Optional<P> read(File file);

  /**
   * write a pojo back to a file
   * @param pojo the pojo to write
   * @param dest the file where to write the pojo
   * @return {@code true} if success
   */
  boolean write(P pojo, File dest);

}
