package com.nodian.nodian_backend.model.account;

import com.nodian.nodian_backend.base.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account extends BaseModel {

  @Column(unique = true)
  private String email;

  private String avatarUrl;

  private String firstName;

  private String lastName;

  private ERole role = ERole.USER;

  public Account (String email, String avatarUrl, String firstName, String lastName) {
    this.email = email;
    this.avatarUrl = avatarUrl;
    this.firstName = firstName;
    this.lastName = lastName;
  }

}
