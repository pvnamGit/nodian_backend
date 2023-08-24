package com.nodian.nodian_backend.model.repo;

import com.nodian.nodian_backend.base.BaseModel;
import com.nodian.nodian_backend.model.account.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repo extends BaseModel {
  private String name;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private Account owner;
}
