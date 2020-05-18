package org.mikufan.cx.common.entity.task.pv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

/**
 * The PV class to simply represent the PV to be download
 * @author CX无敌
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type_")
@JsonSubTypes({
    @JsonSubTypes.Type(value = VocaDbPv.class, name = "VocaDbPv"),
    @JsonSubTypes.Type(value = Pv.class, name = "pv")
})
@EqualsAndHashCode
public class AbstractPv { //is better to have a protected default constructor for jackson for this class and subclasses

  @EqualsAndHashCode.Exclude
  @JsonProperty("title")
  protected String title;

  @JsonProperty("pvId")
  protected String pvId;

  @JsonProperty("service")
  protected String service;
}
