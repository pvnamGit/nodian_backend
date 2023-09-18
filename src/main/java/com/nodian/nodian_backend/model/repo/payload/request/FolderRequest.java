package com.nodian.nodian_backend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderRequest {
  private String name;
  private Long parentFolderId = null;
  private Long repoId;
}
