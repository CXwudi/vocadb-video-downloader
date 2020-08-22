package mikufan.cx.project_vd_common_util.jackson.poc;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Getter @ToString
public abstract class BaseDummyHolder<T> {
  private final T obj;
  private final String note;
}
