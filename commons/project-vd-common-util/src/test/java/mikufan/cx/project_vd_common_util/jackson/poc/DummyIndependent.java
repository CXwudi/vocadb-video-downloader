package mikufan.cx.project_vd_common_util.jackson.poc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
public class DummyIndependent {

  private final int day;

  private final String formatted;

  private final boolean isEmpty; //if no arg constructor exists, boolean with prefix "is" will cause problems with jackson

  private final int month;

  private final int year;
}
