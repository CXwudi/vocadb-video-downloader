package mikufan.cx.vocadbpv_task_producer.config.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * a facade of other config classes.
 * all field are public to allow beautiful code like
 * <blockquote><pre>
 *   myAppConfig.userConfig.getXXX();
 * </pre></blockquote>
 * @author CX无敌
 */
@Getter @RequiredArgsConstructor
public class AppConfig {

  @NonNull
  public final UserConfig userConfig;

  public final SystemConfig systemConfig = SystemConfig.INSTANCE;

}
