package mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ItemsItem implements Comparable<ItemsItem> {

  @JsonProperty("song")
  private Song song;

  @JsonProperty("notes")
  private String notes;

  /**
   * need setter for merging 2 response song list
   */
  @Setter
  @JsonProperty("order")
  private int order;

  @Override
  public String toString() {
    return
        "ItemsItem{" +
            "song = '" + song + '\'' +
            ",notes = '" + notes + '\'' +
            ",order = '" + order + '\'' +
            "}";
  }

  @Override
  public int compareTo(ItemsItem o) {
    return this.order - o.order;
  }
}