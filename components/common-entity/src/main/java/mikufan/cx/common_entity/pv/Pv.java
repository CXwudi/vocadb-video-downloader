package mikufan.cx.common_entity.pv;

import lombok.*;
import mikufan.cx.common_entity.common.PvService;

import java.util.Objects;

/**
 * A simple representation of a pv
 * @author CX无敌
 */
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pv extends AbstractPv{

  public Pv(@NonNull String pvId, @NonNull PvService service, String name) {
    this.pvId = pvId;
    this.service = service;
    this.title = name;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Pv)) {
      return false;
    }
    AbstractPv that = (AbstractPv) o;
    return service == that.service && Objects.equals(pvId, that.pvId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(service, pvId);
  }
}
