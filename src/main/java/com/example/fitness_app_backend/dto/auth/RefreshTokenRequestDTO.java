package com.example.fitness_app_backend.dto.auth;

import lombok.Data;

import java.util.UUID;

@Data
public class RefreshTokenRequestDTO {
    private UUID refreshToken;
}
