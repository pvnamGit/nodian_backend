package com.nodian.nodian_backend.service.note;

import com.nodian.nodian_backend.base.CurrentUser;
import com.nodian.nodian_backend.model.folder.Folder;
import com.nodian.nodian_backend.model.note.Note;
import com.nodian.nodian_backend.model.repo.Repo;
import com.nodian.nodian_backend.payload.request.NoteRequest;
import com.nodian.nodian_backend.repository.folder.FolderRepository;
import com.nodian.nodian_backend.repository.note.NoteRepository;
import com.nodian.nodian_backend.repository.repo.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final FolderRepository folderRepository;
    private final RepoRepository repoRepository;
    private final CurrentUser currentUser;


    private Note checkPermission(Long id) throws AccessDeniedException {
        Note note = noteRepository.findByIdAndAccount(id, currentUser.getCurrentUser()).orElse(null);
        if (note == null) {
            throw new AccessDeniedException("Unauthorized");
        }
        return note;
    }

    private Repo getRepoById(Long id) throws AccessDeniedException {
        Repo repo = repoRepository.findByIdAndOwner(id, currentUser.getCurrentUser()).orElse(null);
        if (repo == null) {
            throw new AccessDeniedException("Unauthorized");
        }
        return repo;
    }

    public Note[] getNotesInRepository(Long repoId) throws AccessDeniedException {
        Repo repo = getRepoById(repoId);
        Note[] notes = noteRepository.findAllByRepository(repo).orElse(null);
        return notes;
    }

    public Note getNote(Long repoId, Long noteId) throws AccessDeniedException {
        Repo repo = getRepoById(repoId);
        Note note = noteRepository.findByIdAndRepository(noteId, repo).orElse(null);
        return note;
    }

    public Note saveContent(Long noteId, NoteRequest noteRequest) throws AccessDeniedException {
        Note note = checkPermission(noteId);
        note.setContent(noteRequest.getContent());
        note.setUpdatedDate(LocalDateTime.now());
        noteRepository.save(note);
        return note;
    }

    public Note createNote(NoteRequest noteRequest) throws AccessDeniedException {
        Repo repo = getRepoById(noteRequest.getRepoId());
        Folder folder = null;
        if (noteRequest.getParentFolderId() != null) {
            folder = folderRepository.findByIdAndRepository(noteRequest.getParentFolderId(), repo).orElse(null);
        }
        Note note = new Note();
        note.setName(noteRequest.getName());
        note.setContent(noteRequest.getContent());
        note.setAccount(currentUser.getCurrentUser());
        note.setParentFolder(folder);
        note.setParentId(folder != null ? folder.getId() : null);
        note.setRepository(repo);
        noteRepository.save(note);
        return note;
    }

    public Note updateNoteName(Long noteId, NoteRequest noteRequest) throws AccessDeniedException {
        Note note = checkPermission(noteId);
        note.setName(noteRequest.getName());
        note.setUpdatedDate(LocalDateTime.now());
        noteRepository.save(note);
        return note;
    }

    public String deleteNote(Long noteId) throws AccessDeniedException {
        Note note = checkPermission(noteId);
        noteRepository.delete(note);
        return "Delete Successfully";
    }

}