package mikufan.cx.vocadbpv_task_producer.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author CX无敌
 */
@Getter @RequiredArgsConstructor
public class AppConfig {

  @NonNull
  private final UserConfig userConfig;

  @NonNull @Getter
  private static final SystemConfig systemConfig = SystemConfig.INSTANCE;

}
