package com.nodian.nodian_backend.base.baseResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {
  private Object data;
  private Boolean status = false;

  public SuccessResponse(Object data) {
    this.data = data;
    this.status = true;
  }
}
