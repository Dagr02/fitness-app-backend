package com.example.fitness_app_backend.dto.auth;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;

    private String password;
}
