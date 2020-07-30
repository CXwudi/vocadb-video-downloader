package mikufan.cx.project_vd_common_util.io;

import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * @param <P>
 * @author CX无敌
 */
public interface PojoFileTransformer<P> {

  /**
   * read a file and return the content in pojo
   * @param file the file to read
   * @return the pojo, can be null
   */
  P read(Path file) throws IOException;

  /**
   * write a pojo back to a file
   * @param pojo the pojo to write
   * @param dest the file where to write the pojo
   * @return {@code true} if success
   */
  boolean write(P pojo, Path dest) throws IOException;

}
