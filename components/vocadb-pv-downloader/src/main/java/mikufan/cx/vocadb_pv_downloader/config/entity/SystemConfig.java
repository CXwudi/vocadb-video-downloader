package mikufan.cx.vocadb_pv_downloader.config.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemConfig {
  static final SystemConfig INSTANCE = createInstance();

  private static SystemConfig createInstance() {
    return new SystemConfig();
  }

}
