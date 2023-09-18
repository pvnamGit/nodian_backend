package com.nodian.nodian_backend.model.note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nodian.nodian_backend.base.BaseController;
import com.nodian.nodian_backend.base.BaseModel;
import com.nodian.nodian_backend.model.account.Account;
import com.nodian.nodian_backend.model.folder.Folder;
import com.nodian.nodian_backend.model.repo.Repo;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Note extends BaseModel {
    
    private String name;
    @Nullable
    private String content = "";
    
    @Nullable
    private Long parentId;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    @Nullable
    private Folder parentFolder;

    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "repo_id")
    private Repo repository;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account account;
}