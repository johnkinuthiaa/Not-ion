package com.slippery.notion.service;

import com.slippery.notion.dto.TasksDto;
import com.slippery.notion.dto.TasksRequest;
import com.slippery.notion.dto.TasksResponse;
import com.slippery.notion.models.Status;

public interface TaskService {
    TasksResponse createNewTask(TasksRequest request, String userId);
    TasksResponse getTaskById(String userId,String TaskId);
    TasksResponse deleteTaskById(String userId,String taskId);
    TasksResponse updateTask(TasksRequest updatedInfo,String userId,String taskId);
    TasksResponse getAllTasksByUser(String userId);
    TasksResponse updateTaskStatus(String userId,String taskId,TasksRequest request);
}
