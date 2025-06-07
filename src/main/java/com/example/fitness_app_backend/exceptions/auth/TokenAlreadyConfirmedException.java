package com.example.fitness_app_backend.exceptions.auth;

public class TokenAlreadyConfirmedException extends RuntimeException {
    public TokenAlreadyConfirmedException(String message) {
        super(message);
    }
}
