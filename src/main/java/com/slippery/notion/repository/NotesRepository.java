package com.slippery.notion.repository;

import com.slippery.notion.models.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes,String> {
}
