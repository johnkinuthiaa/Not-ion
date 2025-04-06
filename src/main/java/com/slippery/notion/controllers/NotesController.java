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
}
