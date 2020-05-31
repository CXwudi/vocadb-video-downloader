package mikufan.cx.common_entity.pv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

/**
 * A simple representation of a pv
 * @author CX无敌
 */
@Getter @ToString @EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({"name", "pvId", "service"})
public class Pv {
  @JsonProperty @NonNull
  protected String pvId;

  @JsonProperty @NonNull
  protected String service;

  @EqualsAndHashCode.Exclude
  @JsonProperty
  protected String name;
}
