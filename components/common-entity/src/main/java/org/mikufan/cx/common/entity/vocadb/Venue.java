package org.mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.eclipse.collections.api.list.MutableList;

@Getter
public class Venue {

  @JsonProperty("address")
  private String address;

  @JsonProperty("names")
  private MutableList<NamesItem> names;

  @JsonProperty("addressCountryCode")
  private String addressCountryCode;

  @JsonProperty("coordinates")
  private Coordinates coordinates;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("webLinks")
  private MutableList<WebLinksItem> webLinks;

  @JsonProperty("id")
  private int id;

  @JsonProperty("version")
  private int version;

  @JsonProperty("additionalNames")
  private String additionalNames;

  @JsonProperty("events")
  private MutableList<EventsItem> events;

  @JsonProperty("status")
  private String status;

  @JsonProperty("deleted")
  private boolean deleted;

  @Override
  public String toString() {
    return
        "Venue{" +
            "address = '" + address + '\'' +
            ",names = '" + names + '\'' +
            ",addressCountryCode = '" + addressCountryCode + '\'' +
            ",coordinates = '" + coordinates + '\'' +
            ",name = '" + name + '\'' +
            ",description = '" + description + '\'' +
            ",webLinks = '" + webLinks + '\'' +
            ",id = '" + id + '\'' +
            ",version = '" + version + '\'' +
            ",additionalNames = '" + additionalNames + '\'' +
            ",events = '" + events + '\'' +
            ",status = '" + status + '\'' +
            ",deleted = '" + deleted + '\'' +
            "}";
  }
}