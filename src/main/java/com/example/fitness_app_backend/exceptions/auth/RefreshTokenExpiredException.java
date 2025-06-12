package com.example.fitness_app_backend.exceptions.auth;

public class RefreshTokenExpiredException extends RuntimeException{
    public RefreshTokenExpiredException(String message){
        super(message);
    }
}
