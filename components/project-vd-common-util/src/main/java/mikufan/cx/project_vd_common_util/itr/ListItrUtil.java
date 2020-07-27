package mikufan.cx.project_vd_common_util.itr;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ListIterator;

/**
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListItrUtil {

  /**
   * return the next element without moving the list iterator
   * @param iterator the itr to get
   * @param <E> type of the next element
   * @return the next element
   */
  public static <E> E nextEle (ListIterator<E> iterator){
    var next = iterator.next();
    iterator.previous();
    return next;
  }
}
