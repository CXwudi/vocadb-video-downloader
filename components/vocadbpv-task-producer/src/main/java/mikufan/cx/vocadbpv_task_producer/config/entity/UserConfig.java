package mikufan.cx.vocadbpv_task_producer.config.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import mikufan.cx.common_entity.common.PvService;
import org.eclipse.collections.api.list.ImmutableList;

import java.io.File;

/**
 * entity class to store user arguments
 * @author CX无敌
 */
@Builder @Getter
public class UserConfig {
  private final int listId;
  @NonNull private final File taskJsonFile;
  @NonNull private final File referenceJsonFile;
  @NonNull private final ImmutableList<PvService> pvPerfOrd;
}
