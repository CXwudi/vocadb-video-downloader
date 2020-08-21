package mikufan.cx.project_vd_common_util.jackson.poc;

import lombok.ToString;

@ToString(callSuper = true)
public class DummyHolder extends BaseDummyHolder<Dummy> {

  public DummyHolder(Dummy obj, String note) {
    super(obj, note);
  }
}
