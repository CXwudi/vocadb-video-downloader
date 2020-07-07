package mikufan.cx.vocadb_pv_task_producer.util.exception;

import mikufan.cx.common_vocaloid_entity.exception.VocalExceptionRCI;

/**
 * RCI code for this module only, {@code MITA} means Miku Task
 */
public enum VocaDbPvTaskRCI implements VocalExceptionRCI {
  //program general issue
  MIKU_TASK_001, MIKU_TASK_002, MIKU_TASK_003, MIKU_TASK_004, MIKU_TASK_005,
  MIKU_TASK_006, MIKU_TASK_007, MIKU_TASK_008, MIKU_TASK_009, MIKU_TASK_010,
  MIKU_TASK_011, MIKU_TASK_012, MIKU_TASK_013,

  //program main functionality issue
  MIKU_TASK_301, MIKU_TASK_302, MIKU_TASK_303, MIKU_TASK_304, MIKU_TASK_305,
  MIKU_TASK_306, MIKU_TASK_307, MIKU_TASK_308, MIKU_TASK_309, MIKU_TASK_310,
  //initializing issue
  MIKU_TASK_901,
}
