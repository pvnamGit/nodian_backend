package com.nodian.adapter.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntityResponse<T> {
    private Boolean success;
    private String message;
    private T data;

    public static <T> BaseEntityResponse<T> success() {
        return BaseEntityResponse.<T>builder()
                .success(Boolean.TRUE)
                .build();
    }

    public static <T> BaseEntityResponse<T> success(String message) {
        return BaseEntityResponse.<T>builder()
                .success(Boolean.TRUE)
                .message(message)
                .build();
    }

    public static <T> BaseEntityResponse<T> success(T data) {
        return BaseEntityResponse.<T>builder()
                .success(Boolean.TRUE)
                .message(null)
                .data(data)
                .build();
    }

    public static <T> BaseEntityResponse<T> error(String message) {
        return BaseEntityResponse.<T>builder()
                .success(Boolean.FALSE)
                .message(message)
                .build();
    }
}
