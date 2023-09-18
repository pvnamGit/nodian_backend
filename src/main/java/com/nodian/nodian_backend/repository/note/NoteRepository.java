package com.nodian.nodian_backend.repository.note;

import com.nodian.nodian_backend.model.account.Account;
import com.nodian.nodian_backend.model.folder.Folder;
import com.nodian.nodian_backend.model.note.Note;
import com.nodian.nodian_backend.model.repo.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note[]> findAllByRepository(Repo repo);
    Optional<Note[]> findAllByParentFolder(Folder folder);
    
    Optional<Note> findByIdAndRepository(Long id, Repo repo);
    
    Optional<Note> findByIdAndAccount(Long id, Account account);
}