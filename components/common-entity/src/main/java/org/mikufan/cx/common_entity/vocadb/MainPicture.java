package org.mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MainPicture {

  @JsonProperty("urlOriginal")
  private String urlOriginal;

  @JsonProperty("urlSmallThumb")
  private String urlSmallThumb;

  @JsonProperty("mime")
  private String mime;

  @JsonProperty("urlThumb")
  private String urlThumb;

  @JsonProperty("urlTinyThumb")
  private String urlTinyThumb;

  @Override
  public String toString() {
    return
        "MainPicture{" +
            "urlOriginal = '" + urlOriginal + '\'' +
            ",urlSmallThumb = '" + urlSmallThumb + '\'' +
            ",mime = '" + mime + '\'' +
            ",urlThumb = '" + urlThumb + '\'' +
            ",urlTinyThumb = '" + urlTinyThumb + '\'' +
            "}";
  }
}