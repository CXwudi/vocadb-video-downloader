package mikufan.cx.vocadbpv_task_producer.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author CX无敌
 */
@Getter @AllArgsConstructor
public class AppConfig {

  @NonNull
  private final UserConfig userConfig;

  @NonNull
  private final SystemConfig systemConfig;

}
