package com.nodian.nodian_backend.model.repo;

import com.nodian.nodian_backend.base.BaseModel;
import com.nodian.nodian_backend.model.account.Account;
import com.nodian.nodian_backend.model.folder.Folder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Repo extends BaseModel {
  private String name;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "owner_id")
  private Account owner;

  @JsonIgnore
  @OneToMany(mappedBy = "repository", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Folder> folders;


  public Repo(String name, Account owner) {
    super();
    this.name = name;
    this.owner = owner;
  }
}
