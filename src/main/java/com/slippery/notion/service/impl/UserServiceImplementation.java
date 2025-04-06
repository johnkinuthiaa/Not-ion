package com.slippery.notion.service.impl;

import com.slippery.notion.dto.UserRequests;
import com.slippery.notion.dto.UserResp;
import com.slippery.notion.dto.UserResponse;
import com.slippery.notion.models.Users;
import com.slippery.notion.repository.UserRepository;
import com.slippery.notion.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse register(UserRequests request) {
        UserResponse response =new UserResponse();
        Users user =new Users();

//        create new user and save
        user.setCreatedAt(LocalDateTime.now());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProfilePhoto(null);
        user.setUpdatedAt(null);
        user.setEmail(request.getEmail());
        userRepository.save(user);

//        create response object
        UserResp createdResponse =createUserResponse(user);

        response.setMessage("New user "+user.getEmail()+" created successfully");
        response.setStatusCode(200);
        response.setUser(createdResponse);
        return response;
    }
    public UserResp createUserResponse(Users user){
        UserResp userResp =new UserResp();
        userResp.setId(user.getId());
        userResp.setCreatedAt(user.getCreatedAt());
        userResp.setUsername(user.getUsername());
        userResp.setProfilePhoto(user.getProfilePhoto());
        userResp.setUpdatedAt(user.getUpdatedAt());
        userResp.setEmail(user.getEmail());
        return userResp;
    }
}
