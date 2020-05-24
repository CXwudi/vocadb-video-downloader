package mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LyricsItem {

  @JsonProperty("cultureCode")
  private String cultureCode;

  @JsonProperty("id")
  private int id;

  @JsonProperty("source")
  private String source;

  @JsonProperty("translationType")
  private String translationType;

  @JsonProperty("value")
  private String value;

  @JsonProperty("url")
  private String url;

  @Override
  public String toString() {
    return
        "LyricsItem{" +
            "cultureCode = '" + cultureCode + '\'' +
            ",id = '" + id + '\'' +
            ",source = '" + source + '\'' +
            ",translationType = '" + translationType + '\'' +
            ",value = '" + value + '\'' +
            ",url = '" + url + '\'' +
            "}";
  }
}