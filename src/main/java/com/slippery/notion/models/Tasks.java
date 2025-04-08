package com.slippery.notion.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    @Lob
    private String description;
    private Status status;
    private Priority priority;
    private Long percentage;
    private LocalDateTime deadline;
    private LocalDateTime createdOn;
    private LocalDateTime completedOn;
    @ManyToOne
    @JsonBackReference
    private Users user;
}
