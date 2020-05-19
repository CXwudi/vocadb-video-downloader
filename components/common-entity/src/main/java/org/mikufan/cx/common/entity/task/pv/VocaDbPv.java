package org.mikufan.cx.common.entity.task.pv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Basically a Pv class but use VocaDB song songId to identify itself.
 * If we have multiply Pv representing the same song but from different service, Pv equals() can't distinguish it.
 * By using the ids from VocaDB, this would not be a problem.
 *
 * @author CX无敌
 */
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VocaDbPv extends AbstractPv {

  @JsonProperty("songId")
  private int songId;

  public VocaDbPv(String pvId, String service, String name, int songId) {
    super(pvId, service, name);
    this.songId = songId;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof AbstractPv && !(o instanceof VocaDbPv)) {
      return super.equals(o);
    }
    if (getClass() != o.getClass()){
      return false;
    }
    VocaDbPv vocaDbPv = (VocaDbPv) o;
    return songId == vocaDbPv.songId;
  }

  @Override
  public final int hashCode() {
    return songId;
  }
}
