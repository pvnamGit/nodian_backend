package com.nodian.nodian_backend.model.account;

import com.nodian.nodian_backend.base.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account extends BaseModel implements UserDetails {

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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
