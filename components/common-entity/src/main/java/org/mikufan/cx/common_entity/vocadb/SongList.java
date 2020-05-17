package org.mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SongList {

  @JsonProperty("name")
  private String name;

  @JsonProperty("id")
  private int id;

  @JsonProperty("featuredCategory")
  private String featuredCategory;

  @Override
  public String toString() {
    return
        "SongList{" +
            "name = '" + name + '\'' +
            ",id = '" + id + '\'' +
            ",featuredCategory = '" + featuredCategory + '\'' +
            "}";
  }
}