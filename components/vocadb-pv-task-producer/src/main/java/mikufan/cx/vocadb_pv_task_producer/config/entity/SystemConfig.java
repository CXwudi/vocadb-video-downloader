package mikufan.cx.vocadb_pv_task_producer.config.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mikufan.cx.project_vd_common_util.config.Assets;
import mikufan.cx.project_vd_common_util.io.JacksonPojoTransformer;
import mikufan.cx.project_vd_common_util.jackson.YamlMapperUtil;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskException;
import mikufan.cx.vocadb_pv_task_producer.util.exception.VocaDbPvTaskRCI;

import java.io.IOException;

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

  /**
   * create the singleton by jackson
   * @return the singleton
   */
  @SneakyThrows(VocaDbPvTaskException.class)
  private static SystemConfig createInstance() {
    val systemConfigFile = Assets.getAssetsRoot().resolve("vocadb-pv-task-producer-system-config.yaml");
    val configReader = JacksonPojoTransformer.createWith(YamlMapperUtil.createDefaultForReadOnly(), SystemConfig.class);

    try {
      //maybe because it is in the class creation stage, we can't use PojoTranslator here, otherwise it reads as linked hashmap.
      return configReader.read(systemConfigFile);
    } catch (IOException e) {
      throw new VocaDbPvTaskException(VocaDbPvTaskRCI.MIKU_TASK_901, "Fail to read system config from " + systemConfigFile, e);
    }
  }


  /**
   * fields here should not declared as final, otherwise jackson won't able to change the value
   */
  @JsonProperty
  private int maxResult = 0;

  /**
   * how many threads can ran in parallel when writing song info json file to output dir
   */
  @JsonProperty
  private int threadCorePoolSize = 0;

  /**
   * max # of threads that can be hold by the thread pool executor,
   * same as max # of songs in a favourite list.
   */
  @JsonProperty
  private int threadMaxPoolSize = 0;

  /**
   * the actual # of thread can be ran in parallel,
   * this is for eclipse-collection's parallel setting, stay same with threadCorePoolSize
   */
  @JsonProperty
  private int batchSize = 0;

  @JsonProperty
  private String errDir;

}
