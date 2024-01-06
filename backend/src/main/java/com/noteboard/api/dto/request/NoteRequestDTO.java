package com.noteboard.api.dto.request;

public record NoteRequestDTO(
    String title,
    String content,
    Long categoryId
) {
}