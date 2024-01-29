package com.noteboard.api.dto.request;

public record RegisterRequest(
        String name,
        String email,
        String password
) {
}
