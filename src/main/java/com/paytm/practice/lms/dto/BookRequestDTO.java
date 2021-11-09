package com.paytm.practice.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class BookRequestDTO {

  @JsonProperty("admin_id")
  private Integer admin_id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("language")
  private String language;

  @JsonProperty("genre")
  private String subject;

  @JsonProperty("author_name")
  private String authorName;

  @JsonProperty("number_of_copies")
  private Integer number_of_copies;

}
