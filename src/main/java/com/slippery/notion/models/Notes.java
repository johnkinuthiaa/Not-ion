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
@Table(indexes = {
        @Index(name = "note_index",columnList ="id" )
})
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    @Lob
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    @ManyToOne
    @JsonBackReference
    private Users user;
}
