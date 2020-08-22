package mikufan.cx.project_vd_common_util.jackson.poc;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Getter @ToString
public abstract class BaseDummy {
  private final String name;
  private final int id;
}
