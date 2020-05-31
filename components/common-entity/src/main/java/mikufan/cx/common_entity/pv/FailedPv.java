package mikufan.cx.common_entity.pv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * indicating something went wrong when performing task on a pv
 * @author CX无敌
 */
@Getter @ToString @EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FailedPv implements Comparable<FailedPv> {

  @JsonProperty("pv")
  private VocaDbPv pv;

  @JsonProperty("reason")
  @EqualsAndHashCode.Exclude
  private String reason;

  @Override
  public int compareTo(FailedPv o) {
    if (o == null){
      //failed pv with empty pv go first
      return 1;
    }
    return this.pv.compareTo(o.getPv());
  }
}
