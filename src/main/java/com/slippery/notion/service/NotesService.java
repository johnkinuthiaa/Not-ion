package com.slippery.notion.service;

import com.slippery.notion.dto.NotesRequest;
import com.slippery.notion.dto.NotesResponse;

public interface NotesService {
    NotesResponse createNewNote(NotesRequest request,String userId);
    NotesResponse findNoteById(String id);
    NotesResponse findAllNotesByUser(String id);
    NotesResponse deleteNoteById(String noteId,String userId);

}
