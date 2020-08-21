package mikufan.cx.project_vd_common_util.jackson.poc;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class Dummy extends BaseDummy {

  private final String status;

  public Dummy(String name, int id, String status) {
    super(name, id);
    this.status = status;
  }
}
