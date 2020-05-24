package mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Coordinates {

  @JsonProperty("formatted")
  private String formatted;

  @JsonProperty("latitude")
  private int latitude;

  @JsonProperty("hasValue")
  private boolean hasValue;

  @JsonProperty("longitude")
  private int longitude;

  @Override
  public String toString() {
    return
        "Coordinates{" +
            "formatted = '" + formatted + '\'' +
            ",latitude = '" + latitude + '\'' +
            ",hasValue = '" + hasValue + '\'' +
            ",longitude = '" + longitude + '\'' +
            "}";
  }
}