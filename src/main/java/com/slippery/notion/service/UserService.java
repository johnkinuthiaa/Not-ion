package com.slippery.notion.service;

import com.slippery.notion.dto.UserRequests;
import com.slippery.notion.dto.UserResponse;

public interface UserService {
    UserResponse register(UserRequests request);
    UserResponse login(UserRequests loginRequest);
    UserResponse findUserById(String userId);
    UserResponse deleteUserById(String userId);
    UserResponse findAllUsers();
}
