package com.noteboard.api.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noteboard.api.persistence.entity.Note;
import com.noteboard.api.persistence.entity.NoteStatus;

@Repository
public interface NoteRepository  extends JpaRepository<Note, Long>{

    List<Note> findByStatus(NoteStatus archived);

    List<Note> findByCategoryId(Long id);

    List<Note> findByStatusAndCategoryId(NoteStatus archived, Long id);

    boolean existsByTitle(String title);
}