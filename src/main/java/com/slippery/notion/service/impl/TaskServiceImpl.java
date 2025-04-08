package com.slippery.notion.service.impl;

import com.slippery.notion.dto.TasksDto;
import com.slippery.notion.dto.TasksRequest;
import com.slippery.notion.dto.TasksResponse;
import com.slippery.notion.models.Priority;
import com.slippery.notion.models.Status;
import com.slippery.notion.models.Tasks;
import com.slippery.notion.repository.TasksRepository;
import com.slippery.notion.repository.UserRepository;
import com.slippery.notion.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    private final TasksRepository repository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper =new ModelMapper();

    public TaskServiceImpl(TasksRepository repository,UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;

    }

    @Override
    public TasksResponse createNewTask(TasksRequest request, String userId) {
        TasksResponse response =new TasksResponse();
        var existingUser =userRepository.findById(userId);
        if(existingUser.isEmpty()){
            response.setMessage("User with id "+userId+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        var userTasks =existingUser.get().getTasks();
        var task =modelMapper.map(request, Tasks.class);
        task.setDeadline(LocalDateTime.parse(request.getDeadline()));
        task.setUser(existingUser.get());
        task.setCreatedOn(LocalDateTime.now());
        task.setCompletedOn(null);
        userTasks.add(task);
        repository.save(task);

        response.setMessage("New task for "+existingUser.get().getUsername()+" created");
        response.setTask(modelMapper.map(task, TasksDto.class));
        response.setStatusCode(201);
        return response;
    }

    @Override
    public TasksResponse getTaskById(String userId, String taskId) {
        TasksResponse response =new TasksResponse();
        var task =repository.findById(taskId);
        var user =userRepository.findById(userId);
        if(user.isEmpty()){
            response.setMessage("User with id "+userId+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        if(task.isEmpty()){
            response.setMessage("Task with id "+taskId+" does not exist");
            response.setStatusCode(404);
            return response;
        }

        var userTasks =user.get().getTasks();
        if(!userTasks.contains(task.get())){
            response.setMessage("User "+user.get().getUsername()+" does not have a task with id "+taskId);
            response.setStatusCode(401);
            return response;
        }
        response.setTask(modelMapper.map(task, TasksDto.class));
        response.setMessage("Task with id "+taskId);
        response.setStatusCode(200);
        return response;
    }

    private boolean findIfTaskExistsById(String taskId) {
        var existingTask =repository.findById(taskId);
        return existingTask.isPresent();
    }

    @Override
    public TasksResponse deleteTaskById(String userId, String taskId) {
        TasksResponse response =new TasksResponse();
        var task =repository.findById(taskId);
        var user =userRepository.findById(userId);
        if(user.isEmpty()){
            response.setMessage("User with id "+userId+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        if(task.isEmpty()){
            response.setMessage("Task with id "+taskId+" does not exist");
            response.setStatusCode(404);
            return response;
        }

        var userTasks =user.get().getTasks();
        if(!userTasks.contains(task.get())){
            response.setMessage("User "+user.get().getUsername()+" does not have a task with id "+taskId);
            response.setStatusCode(401);
            return response;
        }
        userTasks.remove(task.get());
        user.get().setTasks(userTasks);
        repository.delete(task.get());
        response.setMessage("Task with id "+taskId+" deleted successfully");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public TasksResponse updateTask(TasksRequest updatedInfo, String userId, String taskId) {
        return null;
    }

    @Override
    public TasksResponse getAllTasksByUser(String userId) {
        TasksResponse response =new TasksResponse();

        var user =userRepository.findById(userId);
        if(user.isEmpty()){
            response.setMessage("User with id "+userId+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        var userTasks =user.get().getTasks();
        response.setTasks(Arrays.asList(modelMapper.map(userTasks, TasksDto[].class)));
        response.setMessage("All tasks by "+user.get().getUsername());
        response.setStatusCode(200);
        return response;
    }

    @Override
    public TasksResponse updateTaskStatus(String userId, String taskId, TasksRequest request) {
        TasksResponse response =new TasksResponse();
        var task =repository.findById(taskId);
        var user =userRepository.findById(userId);
        if(user.isEmpty()){
            response.setMessage("User with id "+userId+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        if(task.isEmpty()){
            response.setMessage("Task with id "+taskId+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        task.get().setStatus(request.getStatus());
        if(request.getStatus() ==Status.COMPLETED){
            task.get().setCompletedOn(LocalDateTime.now());
            task.get().setPriority(Priority.LOW);
        }
        repository.save(task.get());
        response.setMessage("Task "+taskId+" has been updated successfully");
        response.setStatusCode(206);
        return response;
    }


    public boolean findIfUserExistsById(String userId){
        var existingUser =userRepository.findById(userId);
        return existingUser.isPresent();
    }
}
