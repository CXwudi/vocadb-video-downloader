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
public class FailedVocaDbPv implements Comparable<FailedVocaDbPv> {

  @JsonProperty @NonNull
  private VocaDbPv pv;

  @JsonProperty
  @EqualsAndHashCode.Exclude
  private String reason;

  @Override
  public int compareTo(FailedVocaDbPv o) {
    if (o == null){
      //failed pv with empty pv go first
      return 1;
    }
    return this.pv.compareTo(o.getPv());
  }
}
