package com.noteboard.api.dto.request;

public record AuthenticationRequest(
        String email,
        String password
) {
}
