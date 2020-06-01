package mikufan.cx.vocadbpv_task_producer.config;

import lombok.Builder;
import lombok.Getter;

import java.io.File;

@Builder @Getter
public class UserConfig {
  private final File taskJsonFile;
  private final File referenceJsonFile;
  private final int listId;
}
