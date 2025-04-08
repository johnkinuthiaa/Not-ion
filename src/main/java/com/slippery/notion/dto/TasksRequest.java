package com.slippery.notion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.notion.models.Priority;
import com.slippery.notion.models.Status;
import jakarta.persistence.Lob;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TasksRequest {
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Long percentage;
    private String deadline;
    private LocalDateTime completedOn =null;
}
