package mikufan.cx.common.entity.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import mikufan.cx.common.entity.common.PvService;

import java.util.Comparator;
import java.util.Objects;

/**
 * A simple class for representing a PV.
 * <br\>
 * Note that this class has a specialize {@link Pv2#equals} and {@link Pv2#hashCode} mthods.
 * If we have multiply Pvs representing the same song but from different service,
 * comparing {@link Pv2#service} and {@link Pv2#pvId} can not distinguish.
 * To solve this, we use an extra identifier field {@link Pv2#songId}. (in our case, VocaDB song id)
 * @author CX无敌
 */
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({"name", "pvId", "service", "songId"})
public class Pv2 implements Comparable<Pv2> {
  //a protected default constructor used by jackson only
  /**
   * the website specific id
   */
  @JsonProperty
  @NonNull
  protected String pvId;

  /**
   * which website does it belongs to
   */
  @JsonProperty
  @NonNull
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

  public Pv2(@NonNull String pvId, @NonNull PvService service, String name) {
    this(pvId, service, name, null);
  }

  /**
   * if both this pv and another pv have non empty {@link Pv2#songId}, simply just compare songId.
   * otherwise, compare {@link Pv2#service} and {@link Pv2#pvId}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Pv2)) {
      return false;
    }
    Pv2 pv2 = (Pv2) o;
    if (this.songId != null && !this.songId.isEmpty() &&
        pv2.songId != null && !pv2.songId.isEmpty()){
      return this.songId.equals(pv2.songId);
    } else {
      return Objects.equals(pvId, pv2.pvId) && service == pv2.service;
    }
  }

  /**
   * if both this pv has a non empty {@link Pv2#songId}, simply just hash songId.
   * otherwise, hash both {@link Pv2#service} and {@link Pv2#pvId}
   */
  @Override
  public int hashCode() {
    //TODO: use better hashing algorithm
    return songId != null ? Objects.hash(songId) : Objects.hash(pvId, service);
  }

  @Override
  public int compareTo(Pv2 o) {
    if (this.songId != null && !this.songId.isEmpty() &&
        o.songId != null && !o.songId.isEmpty()){
      return Comparator.comparing(Pv2::getSongId).compare(this, o);
    } else {
      return Comparator
          .comparing(Pv2::getService)
          .thenComparing(Pv2::getPvId)
          .compare(this, o);
    }
  }
}
