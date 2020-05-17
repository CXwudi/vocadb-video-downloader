package org.mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TagsItem {

  @JsonProperty("count")
  private int count;

  @JsonProperty("tag")
  private Tag tag;

  @Override
  public String toString() {
    return
        "TagsItem{" +
            "count = '" + count + '\'' +
            ",tag = '" + tag + '\'' +
            "}";
  }
}