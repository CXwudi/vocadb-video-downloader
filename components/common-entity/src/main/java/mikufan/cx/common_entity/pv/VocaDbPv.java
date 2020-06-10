package mikufan.cx.common_entity.pv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

/**
 * A flatten class for representing a PV with a VocaDB songId to identify itself.
 * <br\>
 * Learn JAVA: if you are not using abstract declaration for instance storing in java,
 * then in pojo-json, should never use inheritence notation like @JsonTypeInfo and @JsonSubTypes.
 *
 * without abstract declaration, we can let json don't contain classes info
 * @author CX无敌
 */
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({"name", "pvId", "service", "songId"})
public class VocaDbPv extends AbstractPv implements Comparable<VocaDbPv> {
  //a protected default constructor used by jackson only

  /**
   * which song does the pv belongs to. this id in vocadb
   */
  @JsonProperty
  protected int songId;

  public VocaDbPv(@NonNull String pvId, @NonNull PvService service, String name, int songId) {
    this.pvId = pvId;
    this.service = service;
    this.title = name;
    this.songId = songId;
  }

  /**
   * simply just compare {@link VocaDbPv#songId}.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof VocaDbPv)) {
      return false;
    }
    VocaDbPv vocaDbPv = (VocaDbPv) o;
    return this.songId == vocaDbPv.songId;
  }

  /**
   * @return {@link VocaDbPv#songId}
   */
  @Override
  public int hashCode() {
    return this.songId;
  }

  /**
   * sorted base on {@link VocaDbPv#songId} <br\>
   * Learn Java: base on the expectation of compareTo.
   * it's hard to do a conditional comparing, so just do a simple sort
   */
  @Override
  public int compareTo(VocaDbPv o) {
    if (o == null){
      return 1;
    }
    return Integer.compare(this.songId, o.songId);
  }
}
