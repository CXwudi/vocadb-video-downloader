package mikufan.cx.common.entity.task;

import java.util.Set;

/**
 * A generic task, it contains a list of task to performance and a lost of task that is done
 *
 * @param <T> the object that the task is about
 * @author CX无敌
 */
public interface Task<T> {

  /**
   * tasks that are not done
   * @return tasks that are not done
   */
  Set<T> getTodos();

  /**
   * tasks that are done
   * @return tasks that are done
   */
  Set<T> getDones();

}
