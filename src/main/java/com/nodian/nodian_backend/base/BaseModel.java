package com.nodian.nodian_backend.base;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@MappedSuperclass
@Lazy(value = false)
public abstract class BaseModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime createdDate = LocalDateTime.now();
  private LocalDateTime updatedDate;
  private Boolean isArchived = false;
}
