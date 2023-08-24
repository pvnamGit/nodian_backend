package com.nodian.nodian_backend.repository.repo;

import com.nodian.nodian_backend.model.account.Account;
import com.nodian.nodian_backend.model.repo.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoRepository extends JpaRepository<Repo, Long> {
  Optional<Repo> findByName (String name);
  Optional<Repo> findByNameAndOwner (String name, Account account);
  Optional<Repo> findByIdAndOwner (Long id, Account account);

  Optional<Repo[]> findByOwner (Account account);

}
