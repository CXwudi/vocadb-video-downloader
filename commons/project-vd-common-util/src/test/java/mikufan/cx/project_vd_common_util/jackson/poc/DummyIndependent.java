package mikufan.cx.project_vd_common_util.jackson.poc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class DummyIndependent {

  private final int day;

  private final String formatted;

  @JsonProperty("isEmpty") // jackson strip out is/get from boolean getter, so make sure add an "is" back to boolean
  private final boolean isEmpty;

  private final boolean stupid;

  private final int month;

  private final int year;
}
