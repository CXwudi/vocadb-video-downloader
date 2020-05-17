package org.mikufan.cx.common_entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
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