package mikufan.cx.vocadbpv_task_producer.config.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemConfig {
  /**
   * the singleton instance of SystemConfig, only same package class can access it.
   * currently only {@link AppConfig} has it
   */
  static final SystemConfig INSTANCE = new SystemConfig();


}
