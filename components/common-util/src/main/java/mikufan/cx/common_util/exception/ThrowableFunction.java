package mikufan.cx.common_util.exception;

import java.util.function.Function;

/**
 * Basically a {@link java.util.function.Function} that can throw exp
 * @author CX无敌
 */
public interface ThrowableFunction<T, R> {
  /**
   * same as {@link Function#apply(Object)}, but can throw exp
   * @throws Exception
   */
  R apply(T t) throws Exception;

  /**
   * convert it to the instance of {@link Function}
   */
  static <T, R> Function<T, R> toFunction(ThrowableFunction<T, R> f){
    return t -> {
      try {
        return f.apply(t);
      } catch (Exception ex) {
        return SneakyThrow.theException(ex);
      }
    };
  }

  /**
   * the instance method of {@link ThrowableFunction#toFunction(ThrowableFunction)}
   */
  default Function<T, R> toFunction(){
    return toFunction(this);
  }
}
