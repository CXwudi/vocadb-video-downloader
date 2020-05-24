package mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemsItem itemsItem = (ItemsItem) o;
    return Objects.equals(song, itemsItem.song);
  }

  @Override
  public int hashCode() {
    return Objects.nonNull(song) ? song.getId() : 0;
  }

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