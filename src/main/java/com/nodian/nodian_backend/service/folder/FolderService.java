package com.nodian.nodian_backend.service.folder;

import com.nodian.nodian_backend.base.CurrentUser;
import com.nodian.nodian_backend.model.folder.Folder;
import com.nodian.nodian_backend.model.repo.Repo;
import com.nodian.nodian_backend.payload.request.FolderRequest;
import com.nodian.nodian_backend.repository.folder.FolderRepository;
import com.nodian.nodian_backend.repository.repo.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;


    private final RepoRepository repoRepository;

    private final CurrentUser currentUser;

    private Folder checkFolderPermission(Long id) throws AccessDeniedException {
        Folder folder = folderRepository.findByIdAndAccount(id, currentUser.getCurrentUser()).orElse(null);
        if (folder == null) {
            throw new AccessDeniedException("Unauthorized");
        }
        return folder;
    }

    private boolean checkFolderAlreadyExisted(String folderName, Long parentId) {
        Folder folder = folderRepository.findByNameAndParentId(folderName, parentId).orElse(null);
        return folder != null;
    }

//    private Folder checkFolderPathExisted(String path, Repo repo) {
//        Folder folder = folderRepository.findByFolderPathAndRepository(path, repo).orElse(null);
//        return folder;
//    }
//
//    private Folder checkParentPathExisted(String path, Repo repo) throws AccessDeniedException {
//        Folder folder = folderRepository.findByFolderPathAndRepository(path, repo).orElse(null);
//        if (folder == null) {
//            throw new AccessDeniedException("Parent path not found");
//        }
//        return folder;
//    }

    public Folder[] getFolders(Long repoId) {
        Repo repo = repoRepository.findById(repoId).orElse(null);
        if (repo != null) {
            return folderRepository.findAllByRepository(repo).orElse(null);
        }
        return null;
    }

    public Folder getFolderById(Long folderId) throws AccessDeniedException {
//    checkFolderPermission(folderId);
        Folder folder = folderRepository.findById(folderId).orElse(null);
        if (folder != null) {
            folderRepository.save(folder);
            return folder;
        } else {
            throw new IllegalArgumentException("Can't find folder");
        }
    }

    public Folder createFolder(FolderRequest request) throws AccessDeniedException {
        if (checkFolderAlreadyExisted(request.getName(), request.getParentFolderId())) {
            throw new IllegalArgumentException("Folder is already existed");
        }
        Repo repo = repoRepository.findById(request.getRepoId()).orElse(null);
        if (repo != null) {
            Folder parentFolder = null;
            if (request.getParentFolderId() != null) {
                parentFolder = folderRepository.findByIdAndRepository(request.getParentFolderId(), repo).orElseThrow(() -> new IllegalArgumentException("Not found parent folder"));
            }

            Folder folder = new Folder();
            folder.setName(request.getName());
            folder.setAccount(currentUser.getCurrentUser());
            folder.setRepository(repo);
            folder.setParentId(parentFolder != null ? parentFolder.getId() : null);
            folder.setParentFolder(parentFolder);

            folderRepository.save(folder);
            return folder;
        } else {
            throw new IllegalArgumentException("Can't find repository");
        }
    }

    public Folder updateFolder(Long folderId, FolderRequest request) throws AccessDeniedException {
        Folder folder = checkFolderPermission(folderId);
        folder.setName(request.getName());
        folderRepository.save(folder);
        return folder;
    }

    public String deleteFolder(Long id) throws AccessDeniedException {
        checkFolderPermission(id);
        Folder folder = folderRepository.findByIdAndAccount(id, currentUser.getCurrentUser()).orElse(null);
        if (folder != null) {
            folderRepository.delete(folder);

            return "Delete folder successful";
        } else {
            throw new IllegalArgumentException("Can't delete folder");
        }
    }
}
