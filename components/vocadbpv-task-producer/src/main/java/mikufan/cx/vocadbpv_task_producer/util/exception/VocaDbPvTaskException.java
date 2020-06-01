package mikufan.cx.vocadbpv_task_producer.util.exception;

import mikufan.cx.common_entity.exception.BaseVocalException;

public class VocaDbPvTaskException extends BaseVocalException {
  public VocaDbPvTaskException(VocaDbPvTaskRCI rci, String message) {
    super(rci, message);
  }

  public VocaDbPvTaskException(VocaDbPvTaskRCI rci, String message, Throwable cause) {
    super(rci, message, cause);
  }
}
