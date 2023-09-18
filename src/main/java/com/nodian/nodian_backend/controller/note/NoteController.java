package com.nodian.nodian_backend.controller.note;

import com.nodian.nodian_backend.base.baseResponse.FailedResponse;
import com.nodian.nodian_backend.base.baseResponse.SuccessResponse;
import com.nodian.nodian_backend.model.note.Note;
import com.nodian.nodian_backend.payload.request.NoteRequest;
import com.nodian.nodian_backend.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/notes")
    public ResponseEntity<?> getNotes(@RequestParam("repoId") Long repoId) {
        try {
            Note[] notes = noteService.getNotesInRepository(repoId);
            return ResponseEntity.ok().body(new SuccessResponse(notes));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new FailedResponse(e.getMessage()));
        }
    }

    @GetMapping("/note/{noteId}")
    public ResponseEntity<?> getNoteById(@PathVariable("noteId") Long noteId, @RequestParam("repoId") Long repoId) {
        try {
            Note note = noteService.getNote(repoId, noteId);
            return ResponseEntity.ok().body(new SuccessResponse(note));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new FailedResponse(e.getMessage()));
        }
    }

    @PostMapping("/note")
    public ResponseEntity<?> createNote(@RequestBody(required = false) NoteRequest request) {
        try {
            Note note = noteService.createNote(request);
            return ResponseEntity.ok().body(new SuccessResponse(note));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new FailedResponse(e.getMessage()));
        }
    }

    @PutMapping("/note/{noteId}")
    public ResponseEntity<?> updateNote(@RequestParam("scope") String scope, @PathVariable("noteId") Long noteId, @RequestBody NoteRequest request) {
        try {
            Note note = scope.equals("content") ? noteService.saveContent(noteId, request) : noteService.updateNoteName(noteId, request);
            return ResponseEntity.ok().body(new SuccessResponse(note));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new FailedResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/note/{noteId}")
    public ResponseEntity<?> deleteNote(@PathVariable("noteId") Long noteId) {
        try {
            String message = noteService.deleteNote(noteId);
            return ResponseEntity.ok().body(new SuccessResponse(message));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new FailedResponse(e.getMessage()));
        }
    }
}