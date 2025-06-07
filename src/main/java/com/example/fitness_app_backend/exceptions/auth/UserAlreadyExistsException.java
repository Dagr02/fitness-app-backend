package com.example.fitness_app_backend.exceptions.auth;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message){
        super(message);
    }
}

