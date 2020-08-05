package mikufan.cx.project_vd_common_util.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Assets {

  public static Path getAssetsRoot(){
    return Path.of("assets");
  }
}
