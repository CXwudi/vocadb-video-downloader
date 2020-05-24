package mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NamesItem {

  @JsonProperty("language")
  private String language;

  @JsonProperty("value")
  private String value;

  @Override
  public String toString() {
    return
        "NamesItem{" +
            "language = '" + language + '\'' +
            ",value = '" + value + '\'' +
            "}";
  }
}