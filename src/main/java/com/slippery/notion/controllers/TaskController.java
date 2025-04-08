package com.slippery.notion.controllers;

import com.slippery.notion.dto.TasksRequest;
import com.slippery.notion.dto.TasksResponse;
import com.slippery.notion.models.Status;
import com.slippery.notion.service.TaskService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping("/{userId}/new")
    public ResponseEntity<TasksResponse> createNewTask(@PathVariable String userId,
                                                       @RequestBody TasksRequest request) {
        var createdTask =service.createNewTask(request, userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(createdTask.getStatusCode())).body(createdTask);
    }

    @GetMapping("/user/{userId}/task/{taskId}")
    public ResponseEntity<TasksResponse> findTaskById(@PathVariable String userId,@PathVariable String taskId) {
        var foundTask =service.getTaskById(userId, taskId);
        return ResponseEntity.status(HttpStatusCode.valueOf(foundTask.getStatusCode())).body(foundTask);
    }
    @DeleteMapping("/user/{userId}/delete/{taskId}")
    public ResponseEntity<TasksResponse> delete(@PathVariable String userId,@PathVariable String taskId) {
        var deletedTask =service.deleteTaskById(userId, taskId);
        return ResponseEntity.status(HttpStatusCode.valueOf(deletedTask.getStatusCode())).body(deletedTask);
    }

    @GetMapping("/user/{userId}/all-tasks")
    public ResponseEntity<TasksResponse> getAllTasksByUser(@PathVariable String userId) {
        var allTasksByUser =service.getAllTasksByUser(userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(allTasksByUser.getStatusCode())).body(allTasksByUser);
    }

    @PatchMapping("/user/{userId}/delete/{taskId}/update-status")
    public ResponseEntity<TasksResponse> updateTaskStatus(@PathVariable String userId,
                                                          @PathVariable String taskId,
                                                          @RequestBody TasksRequest request) {
        var updatedTask =service.updateTaskStatus(userId, taskId, request);
        return ResponseEntity.status(updatedTask.getStatusCode()).body(updatedTask);
    }
}
