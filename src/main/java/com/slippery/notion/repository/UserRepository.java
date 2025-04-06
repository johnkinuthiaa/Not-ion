package com.slippery.notion.repository;

import com.slippery.notion.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,String> {
    Users findByUsername(String username);
}
