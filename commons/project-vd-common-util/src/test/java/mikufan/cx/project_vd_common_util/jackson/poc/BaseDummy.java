package mikufan.cx.project_vd_common_util.jackson.poc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter @ToString
public abstract class BaseDummy {
  private final String name;
  private final int id;
}
