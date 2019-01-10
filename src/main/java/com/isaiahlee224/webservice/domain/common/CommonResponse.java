package com.isaiahlee224.webservice.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
@Data
public class CommonResponse implements Serializable {

  private static final long serialVersionUID = 7173090418820399227L;

  @JsonProperty("result") private String result;
  @JsonProperty("message") private String message;

  @Builder
  public CommonResponse(String result, String message) {
    this.result = result;
    this.message = message;
  }
}
