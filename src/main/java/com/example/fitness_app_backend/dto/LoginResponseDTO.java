package com.example.fitness_app_backend.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;

    private long expiresIn;

}
