package com.slippery.notion.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.notion.models.Priority;
import com.slippery.notion.models.Status;
import com.slippery.notion.models.Users;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TasksDto {
    private String id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Long percentage;
    private LocalDateTime deadline;
    private LocalDateTime createdOn;
    private LocalDateTime completedOn;
//    adding this prevents unending loop
    @JsonBackReference
    private UserResp user;
}
