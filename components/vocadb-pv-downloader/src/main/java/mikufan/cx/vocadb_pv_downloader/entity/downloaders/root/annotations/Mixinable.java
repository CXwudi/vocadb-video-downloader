package mikufan.cx.vocadb_pv_downloader.entity.downloaders.root.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Telling that mixin interface can override this method. <br/>
 * And also contains javadoc explaining the behavior of the mixin interface
 * @author CX无敌
 */
@Target({ElementType.METHOD})
public @interface Mixinable {
  /**
   * explaining how does a mixin interface of this method works
   */
  String effect();
  /**
   * explain how does the order of multiple mixin interfaces of same type works
   */
  String orderEffect() default "order has no affection";

}
