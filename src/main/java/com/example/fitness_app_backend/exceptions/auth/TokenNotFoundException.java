package com.example.fitness_app_backend.exceptions.auth;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super(message);
    }
}
