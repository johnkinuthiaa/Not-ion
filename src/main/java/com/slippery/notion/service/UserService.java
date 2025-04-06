package com.slippery.notion.service;

import com.slippery.notion.dto.UserRequests;
import com.slippery.notion.dto.UserResponse;

public interface UserService {
    UserResponse register(UserRequests request);
}
