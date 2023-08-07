package com.nodian.nodian_backend.payload.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class IdTokenRequestDto {
  private String idToken;

  @JsonCreator
  public IdTokenRequestDto(@JsonProperty("idToken") String idToken) {
    this.idToken = idToken;
  }
}
