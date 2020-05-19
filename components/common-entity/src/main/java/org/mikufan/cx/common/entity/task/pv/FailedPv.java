package org.mikufan.cx.common.entity.task.pv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * indicating something went wrong when performing task on a pv
 * @author CX无敌
 */
@Getter @ToString @EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FailedPv {

  @JsonProperty("pv")
  private AbstractPv pv;

  @JsonProperty("reason")
  @EqualsAndHashCode.Exclude
  private String reason;

}
