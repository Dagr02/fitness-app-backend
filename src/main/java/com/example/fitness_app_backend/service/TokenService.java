package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.model.Token;
import com.example.fitness_app_backend.repository.TokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepo tokenRepo;
    public void saveConfirmationToken(Token token){
        tokenRepo.save(token);
    }
    public Optional<Token> getToken(String token){
        return tokenRepo.findByToken(token);
    }
    public int setConfirmedAt(String token){
        return tokenRepo.updateConfirmedAt(token, LocalDateTime.now());
    }
}
