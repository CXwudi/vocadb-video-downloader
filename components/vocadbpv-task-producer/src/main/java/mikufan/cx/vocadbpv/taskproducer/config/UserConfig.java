package mikufan.cx.vocadbpv.taskproducer.config;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class UserConfig {
  private String name;
  private int count;
}
