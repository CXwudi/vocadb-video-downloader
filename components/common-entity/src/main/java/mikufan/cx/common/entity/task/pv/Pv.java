package mikufan.cx.common.entity.task.pv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import mikufan.cx.common.entity.common.PvService;

import java.util.Comparator;
import java.util.Objects;

/**
 * A simple class for representing a PV.
 * <br\>
 * Note that this class has a specialize {@link Pv#equals} and {@link Pv#hashCode} mthods.
 * If we have multiply Pvs representing the same song but from different service,
 * comparing {@link Pv#service} and {@link Pv#pvId} can not distinguish.
 * To solve this, we use an extra identifier field {@link Pv#songId}. (in our case, VocaDB song id)
 * @author CX无敌
 */
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({"name", "pvId", "service", "songId"})
public class Pv implements Comparable<Pv> {
  //a protected default constructor used by jackson only
  /**
   * the website specific id
   */
  @JsonProperty @NonNull
  protected String pvId;

  /**
   * which website does it belongs to
   */
  @JsonProperty @NonNull
  protected PvService service;

  /**
   * name of the pv, can be null
   */
  @JsonProperty
  protected String name;

  /**
   * optional field, which song does the pv belongs to
   */
  @JsonProperty
  protected String songId;

  public Pv(@NonNull String pvId, @NonNull PvService service, String name) {
    this(pvId, service, name, null);
  }

  /**
   * if both this pv and another pv have non empty {@link Pv#songId}, simply just compare songId.
   * otherwise, compare {@link Pv#service} and {@link Pv#pvId}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Pv)) {
      return false;
    }
    Pv pv = (Pv) o;
    if (this.songId != null && !this.songId.isEmpty() &&
        pv.songId != null && !pv.songId.isEmpty()){
      return this.songId.equals(pv.songId);
    } else {
      return Objects.equals(pvId, pv.pvId) && service == pv.service;
    }
  }

  /**
   * if both this pv has a non empty {@link Pv#songId}, simply just hash songId.
   * otherwise, hash both {@link Pv#service} and {@link Pv#pvId}
   */
  @Override
  public int hashCode() {
    return songId != null ? Objects.hash(songId) : Objects.hash(pvId, service);
  }

  @Override
  public int compareTo(Pv o) {
    if (this.songId != null && !this.songId.isEmpty() &&
        o.songId != null && !o.songId.isEmpty()){
      return Comparator.comparing(Pv::getSongId).compare(this, o);
    } else {
      return Comparator
          .comparing(Pv::getService)
          .thenComparing(Pv::getPvId)
          .compare(this, o);
    }
  }
}
