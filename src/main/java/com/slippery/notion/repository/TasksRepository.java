package com.slippery.notion.repository;

import com.slippery.notion.models.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<Tasks,String> {
}
