package com.nodian.nodian_backend.controller.folder;

import com.nodian.nodian_backend.base.baseResponse.FailedResponse;
import com.nodian.nodian_backend.base.baseResponse.SuccessResponse;
import com.nodian.nodian_backend.model.folder.Folder;
import com.nodian.nodian_backend.payload.request.FolderRequest;
import com.nodian.nodian_backend.service.folder.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @GetMapping("/folders")
    public ResponseEntity<?> getFolders(@RequestParam(name = "repoId") Long repoId) {
        try {
            Folder[] folders = folderService.getFolders(repoId);
            return ResponseEntity.ok().body(new SuccessResponse(folders));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new FailedResponse(exception.getMessage()));
        }
    }

    @GetMapping("/folder/{folderId}")
    public ResponseEntity<?> getFolder(@PathVariable(name = "folderId") Long folderId) {
        try {
            Folder folder = folderService.getFolderById(folderId);
            return ResponseEntity.ok().body(new SuccessResponse(folder));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new FailedResponse(null));
        }
    }

    @PostMapping("/folder")
    public ResponseEntity<?> createFolder(@RequestBody(required = false) FolderRequest request) {
        try {
            Folder folder = folderService.createFolder(request);
            return ResponseEntity.ok().body(new SuccessResponse(folder));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new FailedResponse(exception.getMessage()));
        }
    }

    @PutMapping("/folder/{folderId}")
    public ResponseEntity<?> updateFolder(@PathVariable(name = "folderId") Long folderId,
                                          @RequestBody(required = false) FolderRequest request) {
        try {
            Folder folder = folderService.updateFolder(folderId, request);
            return ResponseEntity.ok().body(new SuccessResponse(folder));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new FailedResponse(exception.getMessage()));
        }
    }

    @DeleteMapping("/folder/{folderId}")
    public ResponseEntity<?> deleteFolder(@PathVariable(name = "folderId") Long folderId) {
        try {
            String deletedMessage = folderService.deleteFolder(folderId);
            return ResponseEntity.ok().body(new SuccessResponse(deletedMessage));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new FailedResponse(exception.getMessage()));
        }
    }
}
