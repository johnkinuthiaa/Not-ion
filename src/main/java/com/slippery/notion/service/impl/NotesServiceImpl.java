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

        repository.save(notes);
//        var userMap =modelMapper.map(existingUser, UserResp.class);
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
        return null;
    }

    @Override
    public NotesResponse findAllNotesByUser(String id) {
        return null;
    }

    @Override
    public NotesResponse deleteNoteById(String id) {
        return null;
    }
}
