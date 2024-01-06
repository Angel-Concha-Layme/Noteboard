package com.noteboard.api.dto.response;

import lombok.Builder;

@Builder
public record CategoryResponseDTO(
    Long id,
    String name
) {
}
