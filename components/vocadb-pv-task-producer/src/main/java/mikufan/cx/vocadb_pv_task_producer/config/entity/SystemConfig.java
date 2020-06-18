package mikufan.cx.vocadb_pv_task_producer.config.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mikufan.cx.common_vocaloid_util.jackson.YamlMapperUtil;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The system-wised config that stays constant for every run of this program
 * @author CX无敌
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SystemConfig {
  /**
   * the singleton instance of SystemConfig, only same package class can access it.
   * currently only {@link AppConfig} has it
   */
  static final SystemConfig INSTANCE = createInstance();

  @JsonProperty
  private final int maxResult = 0;

  /**
   * create the singleton by jackson
   * @return the singleton
   */
  @SneakyThrows(VocaDbPvTaskException.class)
  private static SystemConfig createInstance() {
    val systemConfigFile = Path.of( "config", "system-config.yaml");
    val yamlMapper = YamlMapperUtil.createDefaultForReadOnly();

    try {
      //maybe because it is in the class creation stage, we can't use PojoTranslator here, otherwise it reads as linked hashmap.
      return yamlMapper.readValue(systemConfigFile.toFile(), SystemConfig.class);
    } catch (IOException e) {
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_901, "Fail to read system config from " + systemConfigFile, e);
    }
  }

}
