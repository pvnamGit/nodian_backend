package com.nodian.nodian_backend.controller.repo;

import com.nodian.nodian_backend.base.baseResponse.FailedResponse;
import com.nodian.nodian_backend.base.baseResponse.SuccessResponse;
import com.nodian.nodian_backend.model.repo.Repo;
import com.nodian.nodian_backend.service.repo.RepoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RepoController {

  private final RepoService repoService;


  @PostMapping("/repo")
  public ResponseEntity<?> createRepo(@RequestBody(required = false) String name) {
    try {
      Repo repo = repoService.createRepo(name);
      return ResponseEntity.ok().body(new SuccessResponse(repo));
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(new FailedResponse(exception.getMessage()));
    }
  }

  @GetMapping("/repo/{repoId}")
  public ResponseEntity<?> getRepo(@PathVariable("repoId") Long repoId) {
    try {
      Repo repo = repoService.getRepo(repoId);
      return ResponseEntity.ok().body(new SuccessResponse(repo));
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(new FailedResponse(exception.getMessage()));
    }
  }

  @GetMapping("/repos")
  public ResponseEntity<?> getReposByOwner() {
    try {
      Repo[] repo = repoService.getListReposByOwner();
      return ResponseEntity.ok().body(new SuccessResponse(repo));
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(new FailedResponse(exception.getMessage()));
    }
  }

  @PutMapping("/repo/{repoId}")
  public ResponseEntity updateRepo(@PathVariable("repoId") Long repoId, @RequestBody String newName) {
    try {
      Repo repo = repoService.updateRepo(repoId, newName);
      return ResponseEntity.ok().body(new SuccessResponse(repo));
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(new FailedResponse(exception.getMessage()));
    }
  }


  @DeleteMapping("/repo/{repoId}")
  public ResponseEntity<?> deleteRepo(@PathVariable("repoId") Long repoId) {
    try {
      return ResponseEntity.ok().body(new SuccessResponse(repoService.deleteRepo(repoId)));
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(new FailedResponse(exception.getMessage()));
    }
  }
}
