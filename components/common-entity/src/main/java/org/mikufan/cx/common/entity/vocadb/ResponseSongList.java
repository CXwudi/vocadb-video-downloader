package org.mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.collections.api.set.MutableSet;

/**
 * response object stored for GET /api/songLists/{listId}/songs
 *
 * @author CX无敌
 */
@Getter
public class ResponseSongList {

  @JsonProperty("term")
  private String term;

  @Setter
  @JsonProperty("totalCount")
  private int totalCount;

  @JsonProperty("items")
  private MutableSet<ItemsItem> items;

  @Override
  public String toString() {
    return
        "ResponseSongList{" +
            "term = '" + term + '\'' +
            ",totalCount = '" + totalCount + '\'' +
            ",items = '" + items + '\'' +
            "}";
  }
}