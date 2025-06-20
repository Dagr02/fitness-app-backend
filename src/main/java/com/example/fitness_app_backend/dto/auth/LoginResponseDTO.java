package com.example.fitness_app_backend.dto.auth;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginResponseDTO {
    private String token;
    private long expiresIn;

    private UUID refreshToken;
}
