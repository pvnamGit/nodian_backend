package com.nodian.nodian_backend.base.baseResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FailedResponse {
  private String message;
  private Boolean status = false;

  public FailedResponse(String message) {
    this.message = message;
    this.status = false;
  }
}
