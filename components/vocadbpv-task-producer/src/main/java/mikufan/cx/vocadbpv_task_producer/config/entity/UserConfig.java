package mikufan.cx.vocadbpv_task_producer.config.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;

/**
 * entity class to store user arguments
 * @author CX无敌
 */
@Builder @Getter
public class UserConfig {
  @NonNull private final File taskJsonFile;
  @NonNull private final File referenceJsonFile;
  private final int listId;
}
