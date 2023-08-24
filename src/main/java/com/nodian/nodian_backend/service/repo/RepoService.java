package com.nodian.nodian_backend.service.repo;

import com.nodian.nodian_backend.base.CurrentUser;
import com.nodian.nodian_backend.model.repo.Repo;
import com.nodian.nodian_backend.repository.repo.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepoService {

  private final RepoRepository repoRepository;

  private final CurrentUser currentUser;

  private boolean checkRepoExistedWithOwner(String name) {
    Repo repo = repoRepository.findByNameAndOwner(name, currentUser.getCurrentUser()).orElse(null);
    if (repo != null) {
      throw new IllegalArgumentException("Repo's name already existed");
    }
    return true;
  }

  public Repo getRepo(Long repoId) {
    return repoRepository.findByIdAndOwner(repoId, currentUser.getCurrentUser()).orElse(null);
  }

  public Repo[] getListReposByOwner () {
    return repoRepository.findByOwner(currentUser.getCurrentUser()).orElse(null);
  }

  public Repo createRepo(String name) {
    checkRepoExistedWithOwner(name);
    Repo[] hasRepo = repoRepository.findByOwner(currentUser.getCurrentUser()).orElse(null);

//    if (hasRepo.length > 0) {
//      throw new IllegalArgumentException("Currently, one user only has one repo. Sorry!");
//    }
    Repo newRepo = new Repo(name, currentUser.getCurrentUser());
    repoRepository.save(newRepo);
    return newRepo;
  }

  public Repo updateRepo(Long repoId, String newName) {
    checkRepoExistedWithOwner(newName);
    Repo updateRepo = repoRepository.findByIdAndOwner(repoId, currentUser.getCurrentUser()).orElse(null);
    updateRepo.setName(newName);
    repoRepository.save(updateRepo);
    return updateRepo;
  }

  public String deleteRepo(Long repoId) {
    Repo repo = repoRepository.findByIdAndOwner(repoId, currentUser.getCurrentUser()).orElse(null);
    if (repo == null) {
      throw new IllegalArgumentException("Repo doesn't existed");
    }
    repoRepository.delete(repo);
    return "Delete repo successfully";
  }


}
