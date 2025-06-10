package com.example.fitness_app_backend.dto.auth;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String refreshToken;
    private long expiresIn;


}
