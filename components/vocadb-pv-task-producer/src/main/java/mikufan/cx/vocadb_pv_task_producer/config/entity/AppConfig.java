package mikufan.cx.vocadb_pv_task_producer.config.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.nio.file.Path;

/**
 * contains config from command line, also is a facade of other config classes.
 * all field are public to allow beautiful code like
 * <blockquote><pre>
 *   myAppConfig.userConfig.getXXX();
 * </pre></blockquote>
 * @author CX无敌
 */
@Getter @Builder
public class AppConfig {

  /**
   * the list id, download video from which VocaDB fav list
   */
  private final int listId;

  /**
   * the directory where all json format vocaloid song info are stored
   */
  @NonNull private final Path outputDir;

  /**
   * user configuration read from yaml
   */
  @NonNull public final UserConfig userConfig;

  public final SystemConfig systemConfig = SystemConfig.INSTANCE;



}
