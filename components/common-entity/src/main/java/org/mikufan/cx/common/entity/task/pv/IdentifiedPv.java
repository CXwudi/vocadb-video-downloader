package org.mikufan.cx.common.entity.task.pv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Basically a Pv class but use a songId to identify itself. (In our case, the song id from VocaDb)
 * If we have multiply Pv representing the same song but from different service, Pv equals() can't distinguish it.
 * To solve this, we can use the ids from VocaDB or use other integer identifiers.
 *
 * @author CX无敌
 */
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdentifiedPv extends AbstractPv {

  /**
   * can change it to use String for supporting more than just number identifier
   */
  @JsonProperty("songId")
  protected int songId;

  public IdentifiedPv(String pvId, String service, String name, int songId) {
    super(pvId, service, name);
    this.songId = songId;
  }

  /**
   * A special equals function that can compare with any subclass of {@link AbstractPv}.
   * It has two behaviors:
   * * if {@code o} is instance of this class and its subclass, just compare the {@link IdentifiedPv#songId}
   * * else if {@code o} is instance of the most top parent class {@link AbstractPv}, use {@code super.equals(o)}
   * @return {@code true} if the pv is equals base on this class equals method or super class equals method
   * </br>
   * If overriding this equal method, it should obey the same behavior as describe above
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    var isNotThisType = !(o instanceof IdentifiedPv);
    if (isNotThisType && o instanceof AbstractPv) {
      return super.equals(o);
    }
    if (isNotThisType){
      return false;
    }
    IdentifiedPv identifiedPv = (IdentifiedPv) o;
    return songId == identifiedPv.songId;
  }

  @Override
  public int hashCode() {
    return songId;
  }

  @Override
  public int compareTo(AbstractPv o) {
    //must add this check, otherwise, sorted set fail to maintain distinct pv by song id
    if (o instanceof IdentifiedPv){
      return this.songId - ((IdentifiedPv)o).songId;
    }
    return super.compareTo(o);
  }
}
