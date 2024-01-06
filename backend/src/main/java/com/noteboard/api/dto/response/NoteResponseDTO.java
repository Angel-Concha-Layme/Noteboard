package com.noteboard.api.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record NoteResponseDTO(
    Long id,
    String title,
    String content,
    LocalDateTime creationDate,
    String status,
    Long categoryId
) {
}