package mikufan.cx.vocadb_pv_downloader.config.entity;

import lombok.Builder;
import lombok.NonNull;

import java.nio.file.Path;

/**
 * @author CX无敌
 */
@Builder
public class AppConfig {

  @NonNull private final Path inputDir;
  @NonNull private final Path outputDir;
  @NonNull public final UserConfig userConfig;
  @NonNull public final SystemConfig systemConfig = SystemConfig.INSTANCE;

}
