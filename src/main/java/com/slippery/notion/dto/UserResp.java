package com.slippery.notion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResp {
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String profilePhoto;
    private String email;
    private String id;
    private List<TasksDto> tasks;
}
