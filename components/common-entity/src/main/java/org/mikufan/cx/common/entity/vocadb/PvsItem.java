package org.mikufan.cx.common.entity.vocadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PvsItem {

  @JsonProperty("pvType")
  private String pvType;

  @JsonProperty("author")
  private String author;

  @JsonProperty("length")
  private int length;

  @JsonProperty("publishDate")
  private String publishDate;

  @JsonProperty("url")
  private String url;

  @JsonProperty("createdBy")
  private int createdBy;

  @JsonProperty("pvId")
  private String pvId;

  @JsonProperty("service")
  private String service;

  @JsonProperty("extendedMetadata")
  private ExtendedMetadata extendedMetadata;

  @JsonProperty("name")
  private String name;

  @JsonProperty("disabled")
  private boolean disabled;

  @JsonProperty("id")
  private int id;

  @JsonProperty("thumbUrl")
  private String thumbUrl;

  @Override
  public String toString() {
    return
        "PvsItem{" +
            "pvType = '" + pvType + '\'' +
            ",author = '" + author + '\'' +
            ",length = '" + length + '\'' +
            ",publishDate = '" + publishDate + '\'' +
            ",url = '" + url + '\'' +
            ",createdBy = '" + createdBy + '\'' +
            ",pvId = '" + pvId + '\'' +
            ",service = '" + service + '\'' +
            ",extendedMetadata = '" + extendedMetadata + '\'' +
            ",name = '" + name + '\'' +
            ",disabled = '" + disabled + '\'' +
            ",id = '" + id + '\'' +
            ",thumbUrl = '" + thumbUrl + '\'' +
            "}";
  }
}