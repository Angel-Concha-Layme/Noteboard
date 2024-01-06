package com.noteboard.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.noteboard.api.dto.request.NoteRequestDTO;
import com.noteboard.api.dto.response.NoteResponseDTO;
import com.noteboard.api.exception.CategoryNotFoundException;
import com.noteboard.api.exception.NoteNotFoundException;
import com.noteboard.api.persistence.entity.Category;
import com.noteboard.api.persistence.entity.Note;
import com.noteboard.api.persistence.entity.NoteStatus;
import com.noteboard.api.persistence.repository.CategoryRepository;
import com.noteboard.api.persistence.repository.NoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoteService {

        private NoteRepository noteRepository;
        private CategoryRepository categoryRepository;

        public NoteResponseDTO createNote(NoteRequestDTO noteRequestDTO) {
                Note createdNote = new Note();
                createdNote.setTitle(noteRequestDTO.title());
                createdNote.setContent(noteRequestDTO.content());
                createdNote.setCreationDate(LocalDateTime.now());
                createdNote.setStatus(NoteStatus.ACTIVE);

                Category category = categoryRepository.findById(noteRequestDTO.categoryId())
                                .orElseThrow(() -> new CategoryNotFoundException(
                                                "La categoría con id " + noteRequestDTO.categoryId() + " no existe."));
                createdNote.setCategory(category);

                createdNote = noteRepository.save(createdNote);

                return NoteResponseDTO.builder()
                                .id(createdNote.getId())
                                .title(createdNote.getTitle())
                                .content(createdNote.getContent())
                                .creationDate(createdNote.getCreationDate())
                                .status(createdNote.getStatus().toString())
                                .categoryId(createdNote.getCategory().getId())
                                .build();
        }

        public void deleteNote(Long id) {
                try {
                        Note note = noteRepository.findById(id)
                                        .orElseThrow(() -> new NoSuchElementException(
                                                        "La nota con id " + id + " no existe."));
                        noteRepository.delete(note);
                } catch (NoSuchElementException e) {
                        throw new NoteNotFoundException(e.getMessage());
                }
        }

        public List<NoteResponseDTO> getAllNotes() {
                List<Note> notes = noteRepository.findAll();
                return notes.stream()
                                .map(note -> NoteResponseDTO.builder()
                                                .id(note.getId())
                                                .title(note.getTitle())
                                                .content(note.getContent())
                                                .creationDate(note.getCreationDate())
                                                .status(note.getStatus().toString())
                                                .categoryId(note.getCategory().getId())
                                                .build())
                                .collect(Collectors.toList());
        }

        public NoteResponseDTO updateNote(Long id, NoteRequestDTO noteRequestDTO) {
                Note note = noteRepository.findById(id)
                                .orElseThrow(() -> new NoteNotFoundException("La nota con id " + id + " no existe."));

                note.setTitle(noteRequestDTO.title());
                note.setContent(noteRequestDTO.content());

                Category category = categoryRepository.findById(noteRequestDTO.categoryId())
                                .orElseThrow(() -> new CategoryNotFoundException(
                                                "La categoría con id " + noteRequestDTO.categoryId() + " no existe."));
                note.setCategory(category);

                note = noteRepository.save(note);

                return NoteResponseDTO.builder()
                                .id(note.getId())
                                .title(note.getTitle())
                                .content(note.getContent())
                                .creationDate(note.getCreationDate())
                                .status(note.getStatus().toString())
                                .categoryId(note.getCategory().getId())
                                .build();

        }

        public NoteResponseDTO archiveNoteById(Long id) {
                Note note = noteRepository.findById(id)
                                .orElseThrow(() -> new NoteNotFoundException("La nota con id " + id + " no existe."));

                note.setStatus(NoteStatus.ARCHIVED);

                note = noteRepository.save(note);

                return NoteResponseDTO.builder()
                                .id(note.getId())
                                .title(note.getTitle())
                                .content(note.getContent())
                                .creationDate(note.getCreationDate())
                                .status(note.getStatus().toString())
                                .categoryId(note.getCategory().getId())
                                .build();
        }

        public NoteResponseDTO activeNoteById(Long id) {
                Note note = noteRepository.findById(id)
                                .orElseThrow(() -> new NoteNotFoundException("La nota con id " + id + " no existe."));

                note.setStatus(NoteStatus.ACTIVE);

                note = noteRepository.save(note);

                return NoteResponseDTO.builder()
                                .id(note.getId())
                                .title(note.getTitle())
                                .content(note.getContent())
                                .creationDate(note.getCreationDate())
                                .status(note.getStatus().toString())
                                .categoryId(note.getCategory().getId())
                                .build();
        }

        public List<NoteResponseDTO> getAllArchivedNotes() {
                List<Note> notes = noteRepository.findByStatus(NoteStatus.ARCHIVED);
                return notes.stream()
                                .map(note -> NoteResponseDTO.builder()
                                                .id(note.getId())
                                                .title(note.getTitle())
                                                .content(note.getContent())
                                                .creationDate(note.getCreationDate())
                                                .status(note.getStatus().toString())
                                                .categoryId(note.getCategory().getId())
                                                .build())
                                .collect(Collectors.toList());
        }

        public List<NoteResponseDTO> getAllActiveNotes() {
                List<Note> notes = noteRepository.findByStatus(NoteStatus.ACTIVE);
                return notes.stream()
                                .map(note -> NoteResponseDTO.builder()
                                                .id(note.getId())
                                                .title(note.getTitle())
                                                .content(note.getContent())
                                                .creationDate(note.getCreationDate())
                                                .status(note.getStatus().toString())
                                                .categoryId(note.getCategory().getId())
                                                .build())
                                .collect(Collectors.toList());
        }

        public List<NoteResponseDTO> getAllNotesByCategoryId(Long id) {
                List<Note> notes = noteRepository.findByCategoryId(id);
                return notes.stream()
                                .map(note -> NoteResponseDTO.builder()
                                                .id(note.getId())
                                                .title(note.getTitle())
                                                .content(note.getContent())
                                                .creationDate(note.getCreationDate())
                                                .status(note.getStatus().toString())
                                                .categoryId(note.getCategory().getId())
                                                .build())
                                .collect(Collectors.toList());
        }

        public List<NoteResponseDTO> getAllArchivedNotesByCategoryId(Long id) {
                List<Note> notes = noteRepository.findByStatusAndCategoryId(NoteStatus.ARCHIVED, id);
                return notes.stream()
                                .map(note -> NoteResponseDTO.builder()
                                                .id(note.getId())
                                                .title(note.getTitle())
                                                .content(note.getContent())
                                                .creationDate(note.getCreationDate())
                                                .status(note.getStatus().toString())
                                                .categoryId(note.getCategory().getId())
                                                .build())
                                .collect(Collectors.toList());
                
        }

        public List<NoteResponseDTO> getAllActiveNotesByCategoryId(Long id) {
                List<Note> notes = noteRepository.findByStatusAndCategoryId(NoteStatus.ACTIVE, id);
                return notes.stream()
                                .map(note -> NoteResponseDTO.builder()
                                                .id(note.getId())
                                                .title(note.getTitle())
                                                .content(note.getContent())
                                                .creationDate(note.getCreationDate())
                                                .status(note.getStatus().toString())
                                                .categoryId(note.getCategory().getId())
                                                .build())
                                .collect(Collectors.toList());
        }
}
