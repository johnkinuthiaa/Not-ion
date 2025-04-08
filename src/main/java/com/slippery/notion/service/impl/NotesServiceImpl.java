package com.slippery.notion.service.impl;

import com.slippery.notion.dto.NotesDto;
import com.slippery.notion.dto.NotesRequest;
import com.slippery.notion.dto.NotesResponse;
import com.slippery.notion.dto.UserResp;
import com.slippery.notion.models.Notes;
import com.slippery.notion.models.Users;
import com.slippery.notion.repository.NotesRepository;
import com.slippery.notion.repository.UserRepository;
import com.slippery.notion.service.NotesService;
import com.slippery.notion.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {
    private final NotesRepository repository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper =new ModelMapper();

    public NotesServiceImpl(NotesRepository repository, UserService userService, UserRepository userRepository) {
        this.repository = repository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public NotesResponse createNewNote(NotesRequest request, String userId) {
        NotesResponse response =new NotesResponse();
        Optional<Users> existingUser =userRepository.findById(userId);

        Notes notes =new Notes();

//        check if user exists
        if(existingUser.isEmpty()){
            response.setMessage("User with id "+userId+" does not exist");
            response.setStatusCode(404);
            return response;
        }
//        create a note object
        notes.setContent(request.getContent());
        notes.setTitle(request.getTitle());
        notes.setUpdatedOn(null);
        notes.setCreatedOn(LocalDateTime.now());
        notes.setUser(existingUser.get());


        var userNotes =existingUser.get().getNotes();
        userNotes.add(notes);
        repository.save(notes);

//        convert the notes to notes dto
        var noteDto =modelMapper.map(notes, NotesDto.class);

        response.setStatusCode(201);
        response.setMessage("New note for "+
                existingUser.get().getUsername() +
                " created successfully");
        response.setNote(noteDto);
        return response;
    }
    @Override
    public NotesResponse findNoteById(String id) {
        NotesResponse response =new NotesResponse();
        var existingNote =repository.findById(id);
        if(existingNote.isEmpty()){
            response.setMessage("The note with id "+id+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        var noteResponse =modelMapper.map(existingNote, NotesDto.class);
        response.setNote(noteResponse);
        response.setStatusCode(200);
        response.setMessage("The note with id "+id+" .");
        return response;
    }

    @Override
    public NotesResponse findAllNotesByUser(String userId) {
        NotesResponse response =new NotesResponse();
        var existingUser =userService.findUserById(userId);
        if(existingUser.getStatusCode() !=200){
            return modelMapper.map(existingUser, NotesResponse.class);
        }
        var userNotes =repository.findAll()
                .stream().filter(notes -> notes.getUser().getId().equals(userId)).toList();
        var noteList = Arrays.asList(modelMapper.map(userNotes, NotesDto[].class));

        response.setStatusCode(200);
        response.setNotesList(noteList);
        response.setMessage("All notes for "+existingUser.getUser().getUsername());
        return response;
    }

    @Override
    public NotesResponse deleteNoteById(String noteId,String userId) {
        NotesResponse response =new NotesResponse();
        var existingNote =findNoteById(noteId);
        var existingUser =userRepository.findById(userId);

        if(existingUser.isEmpty()){
            response.setMessage("No user with id" +userId+" was found.");
            response.setStatusCode(404);
            return response;
        }
        if(existingNote.getStatusCode() !=200){
            return existingNote;
        }

//        Todo: will come to fix here(ensure a user only deletes their note.
        var note =modelMapper.map(existingNote.getNote(), Notes.class);
        var userNotes =existingUser.get().getNotes();
        userNotes.remove(note);
        existingUser.get().setNotes(userNotes);
        userRepository.save(existingUser.get());
        repository.deleteById(noteId);
        response.setMessage("Note with id "+noteId+" deleted successfully");
        response.setStatusCode(200);
        return response;
    }
}
