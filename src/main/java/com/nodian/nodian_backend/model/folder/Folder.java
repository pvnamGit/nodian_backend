package com.nodian.nodian_backend.model.folder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nodian.nodian_backend.base.BaseModel;
import com.nodian.nodian_backend.model.account.Account;
import com.nodian.nodian_backend.model.repo.Repo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Folder extends BaseModel {

    private String name;
    
    private Long parentId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    @Nullable
    private Folder parentFolder;

    @JsonIgnore
    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> childFolder;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "repo_id")
    private Repo repository;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account account;

}
