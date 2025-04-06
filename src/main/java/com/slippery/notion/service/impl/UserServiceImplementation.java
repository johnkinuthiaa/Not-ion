package com.slippery.notion.service.impl;

import com.slippery.notion.dto.UserRequests;
import com.slippery.notion.dto.UserResp;
import com.slippery.notion.dto.UserResponse;
import com.slippery.notion.models.Users;
import com.slippery.notion.repository.UserRepository;
import com.slippery.notion.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;

    public UserServiceImplementation(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
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
        response.setStatusCode(201);
        response.setUser(createdResponse);
        return response;
    }

    @Override
    public UserResponse login(UserRequests loginRequest) {
        UserResponse response =new UserResponse();
        try{
            Authentication authentication =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
            );
            if(authentication.isAuthenticated()){
                response.setMessage("User authenticated successfully");
                response.setStatusCode(200);
                return response;
            }
        } catch (Exception e) {
            response.setMessage(e.getLocalizedMessage());
            response.setStatusCode(401);
        }
        return response;
    }

    @Override
    public UserResponse findUserById(String userId) {
        UserResponse response =new UserResponse();
        var user =userRepository.findById(userId);
        if(user.isEmpty()){
            response.setMessage("Not user with id "+userId+" was found");
            response.setStatusCode(404);
            return response;
        }
        response.setMessage("User with id "+userId);
        response.setStatusCode(200);
        response.setUser(createUserResponse(user.get()));
        return response;
    }

    @Override
    public UserResponse deleteUserById(String userId) {
        UserResponse response =new UserResponse();
        var existingUser =findUserById(userId);
        if(existingUser.getStatusCode() !=200){
            return existingUser;
        }
        userRepository.deleteById(userId);
        response.setMessage("User with id" +userId+" deleted successfully");
        response.setStatusCode(204);
        return response;
    }

    @Override
    public UserResponse findAllUsers() {
        UserResponse response =new UserResponse();
        var userList =userRepository.findAll();
        List<UserResp> users =new ArrayList<>();
        if(userList.isEmpty()){
            response.setStatusCode(404);
            response.setMessage("No users in the database");
            return response;
        }
//        find a better war for mapping users to the response because this uses O(n) time and will be slow later

        for(Users user :userList){
            users.add(createUserResponse(user));
        }
        response.setUsers(users);
        response.setStatusCode(200);
        response.setMessage("All users in the database");
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
