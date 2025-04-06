package com.slippery.notion.controllers;

import com.slippery.notion.dto.UserRequests;
import com.slippery.notion.dto.UserResponse;
import com.slippery.notion.service.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequests requests) {
        var createdUser =service.register(requests);
        return ResponseEntity.status(HttpStatusCode.valueOf(createdUser.getStatusCode())).body(createdUser);
    }
}
