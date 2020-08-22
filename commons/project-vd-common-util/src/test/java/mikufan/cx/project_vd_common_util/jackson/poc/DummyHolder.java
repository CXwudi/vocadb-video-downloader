package mikufan.cx.project_vd_common_util.jackson.poc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class DummyHolder extends BaseDummyHolder<Dummy> {

  public DummyHolder(Dummy obj, String note) {
    super(obj, note);
  }
}
