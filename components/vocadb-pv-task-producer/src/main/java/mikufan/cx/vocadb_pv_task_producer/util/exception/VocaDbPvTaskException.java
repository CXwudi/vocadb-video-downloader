package mikufan.cx.vocadb_pv_task_producer.util.exception;

import mikufan.cx.common_vocaloid_entity.exception.BaseVocalException;

public class VocaDbPvTaskException extends BaseVocalException {

  private static final long serialVersionUID = 6132876976331751082L; //some generated uid

  public VocaDbPvTaskException(VocaDbPvTaskRCI rci, String message) {
    super(rci, message);
  }

  public VocaDbPvTaskException(VocaDbPvTaskRCI rci, String message, Throwable cause) {
    super(rci, message, cause);
  }
}
