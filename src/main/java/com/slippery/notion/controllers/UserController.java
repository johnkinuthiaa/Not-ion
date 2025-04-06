package com.slippery.notion.controllers;

import com.slippery.notion.dto.UserRequests;
import com.slippery.notion.dto.UserResponse;
import com.slippery.notion.service.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequests loginRequest) {
        var loggedInUser =service.login(loginRequest);
        return ResponseEntity.status(HttpStatusCode.valueOf(loggedInUser.getStatusCode())).body(loggedInUser);
    }

    @GetMapping("/{userId}/get")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userId) {
        var foundUser =service.findUserById(userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(foundUser.getStatusCode())).body(foundUser);
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable String userId) {
        var deletedUser =service.deleteUserById(userId);
        return ResponseEntity.ok(deletedUser);
    }

    @GetMapping("/all")
    public ResponseEntity<UserResponse> getAll() {
        var allUsers =service.findAllUsers();
        return ResponseEntity.status(allUsers.getStatusCode()).body(allUsers);
    }
}
