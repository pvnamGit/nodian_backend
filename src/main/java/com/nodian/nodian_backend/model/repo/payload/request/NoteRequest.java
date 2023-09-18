package com.nodian.nodian_backend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
public class NoteRequest {
    @Nullable
    private String name;

    @Nullable
    private String content;
    
    @Nullable
    private Long parentFolderId;
    private Long repoId;
    
}