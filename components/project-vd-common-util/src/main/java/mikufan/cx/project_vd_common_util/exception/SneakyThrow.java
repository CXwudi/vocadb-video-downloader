package mikufan.cx.project_vd_common_util.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * just an util class to sneaky throw an exp
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SneakyThrow {

  @SuppressWarnings("unchecked")
  public static <T extends Exception, R> R theException(Exception t) throws T {
    throw (T) t; // ( ͡° ͜ʖ ͡°)
  }
}
