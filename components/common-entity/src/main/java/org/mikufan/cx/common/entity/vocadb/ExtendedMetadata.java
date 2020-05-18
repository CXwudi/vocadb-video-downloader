package org.mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ExtendedMetadata {

  @JsonProperty("json")
  private String json;

  @Override
  public String toString() {
    return
        "ExtendedMetadata{" +
            "json = '" + json + '\'' +
            "}";
  }
}