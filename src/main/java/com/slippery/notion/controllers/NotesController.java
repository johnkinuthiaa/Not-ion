package com.slippery.notion.controllers;

import com.slippery.notion.dto.NotesRequest;
import com.slippery.notion.dto.NotesResponse;
import com.slippery.notion.dto.UserResponse;
import com.slippery.notion.service.NotesService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
    private final NotesService service;

    public NotesController(NotesService service) {
        this.service = service;
    }

    @PostMapping("/create/user/{userId}")
    public ResponseEntity<NotesResponse> createNote(@RequestBody NotesRequest request, @PathVariable String userId) {
        var createdNote = service.createNewNote(request, userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(createdNote.getStatusCode())).body(createdNote);
    }

    @GetMapping("/{noteId}/find")
    public ResponseEntity<NotesResponse> getNoteById(@PathVariable String noteId) {
        var foundNote =service.findNoteById(noteId);
        return ResponseEntity.status(HttpStatusCode.valueOf(foundNote.getStatusCode())).body(foundNote);
    }

    @GetMapping("/{userId}/all")
    public ResponseEntity<NotesResponse> getAllNotesByUser(@PathVariable String userId) {
        var allNotesByUser =service.findAllNotesByUser(userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(allNotesByUser.getStatusCode())).body(allNotesByUser);
    }

    @DeleteMapping("/{userId}/user/delete-note/{noteId}")
    public ResponseEntity<NotesResponse> deleteNoteById(@PathVariable String noteId,@PathVariable String userId) {
        var deletedNote =service.deleteNoteById(noteId,userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(deletedNote.getStatusCode())).body(deletedNote);
    }
}
