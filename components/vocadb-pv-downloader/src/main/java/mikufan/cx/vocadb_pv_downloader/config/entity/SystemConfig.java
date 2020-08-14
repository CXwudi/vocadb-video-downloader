package mikufan.cx.vocadb_pv_downloader.config.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import mikufan.cx.project_vd_common_util.config.Assets;
import mikufan.cx.project_vd_common_util.io.JacksonPojoTransformer;
import mikufan.cx.project_vd_common_util.jackson.YamlMapperUtil;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlException;
import mikufan.cx.vocadb_pv_downloader.util.exception.VocaDbPvDlRCI;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SystemConfig {
  static final SystemConfig INSTANCE = createInstance();

  @SneakyThrows
  private static SystemConfig createInstance() {
    var sysConfFile = Assets.getAssetsRoot().resolve("vocadb-pv-downloader-system-config.yaml");
    var confReader = JacksonPojoTransformer.createWith(YamlMapperUtil.createDefaultForReadOnly(), SystemConfig.class);

    try {
      return confReader.read(sysConfFile);
    } catch (IOException e) {
      throw new VocaDbPvDlException(VocaDbPvDlRCI.MIKU_DL_008, "Can't read the system config file", e);
    }
  }

  @JsonProperty
  private Path youtubeDlWinPath;

  @JsonProperty
  private Path youtubeDlLinuxPath;
}
