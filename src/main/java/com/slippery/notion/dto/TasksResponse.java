package com.slippery.notion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TasksResponse {
    private String message;
    private int statusCode;
    private TasksDto task;
    private List<TasksDto> tasks;
}
