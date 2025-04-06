package com.slippery.notion.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "multi_column_index", columnList = "id, username,email"),
        @Index(name = "id", columnList = "id"),
        @Index(name = "auth", columnList = "username,password"),
        @Index(name = "username", columnList = "username"),
        @Index(name = "email", columnList = "email"),
        @Index(name = "profile_photo", columnList = "profilePhoto")
})
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String profilePhoto;
    private String email;
    @JsonManagedReference
    @OneToMany
    private List<Notes> notes =new ArrayList<>();

}
