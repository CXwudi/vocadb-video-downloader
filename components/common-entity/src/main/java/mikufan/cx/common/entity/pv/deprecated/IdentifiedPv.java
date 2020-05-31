package mikufan.cx.common.entity.pv.deprecated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mikufan.cx.common.entity.pv.VocaDbPv;


/**
 * Basically a PvOld class but use a songId to identify itself. (In our case, the song id from VocaDb)
 * If we have multiply PvOld representing the same song but from different service, PvOld equals() can't distinguish it.
 * To solve this, we can use the ids from VocaDB or use other integer identifiers.
 *
 * @author CX无敌
 * @deprecated use the new {@link VocaDbPv} class instead
 */
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Deprecated(since = "4.0.0")
public class IdentifiedPv extends AbstractPv implements Comparable<IdentifiedPv>{

  /**
   * can change it to use String for supporting more than just number identifier
   */
  @JsonProperty("songId")
  protected int songId;

  public IdentifiedPv(String pvId, String service, String name, int songId) {
    super(pvId, service, name);
    this.songId = songId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IdentifiedPv)) {
      return false;
    }
    IdentifiedPv that = (IdentifiedPv) o;
    return songId == that.songId;
  }

  @Override
  public int hashCode() {
    return songId;
  }

  @Override
  public int compareTo(IdentifiedPv o) {
    return Integer.compare(this.songId, o.songId);
  }
}
