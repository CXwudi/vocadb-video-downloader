package mikufan.cx.task_producer;

/**
 *
 * @author CX无敌
 */
public interface UpdatableTaskProducer<T> extends GeneralTaskProducer {

  /**
   * create a new task or update an existing task if {@code task} presents
   * @param task
   * @return
   */
  default T createOrUpdate(T task) {
    if (task == null){
      return create();
    } else {
      return update(task);
    }
  }

  /**
   * create a new task
   * @return new task
   */
  T create();

  /**
   * update an existing task
   * @param task the old task to be updated
   * @return new updated task
   */
  T update(T task);
}
