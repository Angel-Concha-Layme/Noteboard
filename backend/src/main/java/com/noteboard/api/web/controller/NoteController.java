package com.noteboard.api.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noteboard.api.dto.request.NoteRequestDTO;
import com.noteboard.api.dto.response.NoteResponseDTO;
import com.noteboard.api.service.NoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/notes")
@AllArgsConstructor
public class NoteController {

    private NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(@RequestBody NoteRequestDTO noteRequestDTO) {
        NoteResponseDTO noteResponseDTO = noteService.createNote(noteRequestDTO);
        return ResponseEntity.ok(noteResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<NoteResponseDTO>> getAllNotes() {
        List<NoteResponseDTO> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> updateNote(@PathVariable Long id, @RequestBody NoteRequestDTO noteRequestDTO) {
        NoteResponseDTO noteResponseDTO = noteService.updateNote(id, noteRequestDTO);
        return ResponseEntity.ok(noteResponseDTO);
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<NoteResponseDTO> archiveNote(@PathVariable Long id) {
        NoteResponseDTO noteResponseDTO = noteService.archiveNoteById(id);
        return ResponseEntity.ok(noteResponseDTO);
    }
    
    @PutMapping("/{id}/active")
    public ResponseEntity<NoteResponseDTO> activeNoteById(@PathVariable Long id) {
        NoteResponseDTO noteResponseDTO = noteService.activeNoteById(id);
        return ResponseEntity.ok(noteResponseDTO);
    }

    @GetMapping("/archived")
    public ResponseEntity<List<NoteResponseDTO>> getAllArchivedNotes() {
        List<NoteResponseDTO> notes = noteService.getAllArchivedNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/active")
    public ResponseEntity<List<NoteResponseDTO>> getAllActiveNotes() {
        List<NoteResponseDTO> notes = noteService.getAllActiveNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<NoteResponseDTO>> getAllNotesByCategoryId(@PathVariable Long id) {
        List<NoteResponseDTO> notes = noteService.getAllNotesByCategoryId(id);
        return ResponseEntity.ok(notes);
    }
    
    @GetMapping("/archived/category/{id}")
    public ResponseEntity<List<NoteResponseDTO>> getAllArchivedNotesByCategoryId(@PathVariable Long id) {
        List<NoteResponseDTO> notes = noteService.getAllArchivedNotesByCategoryId(id);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/active/category/{id}")
    public ResponseEntity<List<NoteResponseDTO>> getAllActiveNotesByCategoryId(@PathVariable Long id) {
        List<NoteResponseDTO> notes = noteService.getAllActiveNotesByCategoryId(id);
        return ResponseEntity.ok(notes);
    }
}
