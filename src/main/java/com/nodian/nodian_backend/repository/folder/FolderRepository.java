package com.nodian.nodian_backend.repository.folder;

import com.nodian.nodian_backend.model.account.Account;
import com.nodian.nodian_backend.model.folder.Folder;
import com.nodian.nodian_backend.model.repo.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

  Optional<Folder> findById(Long id);

  Optional<Folder> findByIdAndRepository(Long id, Repo repo);
  
  Optional<Folder> findByNameAndParentId(String name, Long parentId);

  Optional<Folder[]> findAllByRepository(Repo repository);

  Optional<Folder[]> findAllByAccount(Account account);

  Optional<Folder[]> findAllByRepositoryAndAccount(Repo repo, Account account);

  Optional<Folder> findByIdAndAccount(Long id, Account account);

}
