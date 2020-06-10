package mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ReleaseDate {

  @JsonProperty("month")
  private int month;

  @JsonProperty("year")
  private int year;

  @JsonProperty("formatted")
  private String formatted;

  @JsonProperty("isEmpty")
  private boolean isEmpty;

  @JsonProperty("day")
  private int day;

  @Override
  public String toString() {
    return
        "ReleaseDate{" +
            "month = '" + month + '\'' +
            ",year = '" + year + '\'' +
            ",formatted = '" + formatted + '\'' +
            ",isEmpty = '" + isEmpty + '\'' +
            ",day = '" + day + '\'' +
            "}";
  }
}