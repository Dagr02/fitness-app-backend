package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.dto.auth.LoginResponseDTO;
import com.example.fitness_app_backend.exceptions.ResourceNotFoundException;
import com.example.fitness_app_backend.model.RefreshToken;
import com.example.fitness_app_backend.model.User;
import com.example.fitness_app_backend.repository.RefreshTokenRepo;
import com.example.fitness_app_backend.repository.UserRepo;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);

    private final RefreshTokenRepo refreshTokenRepo;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final UserService userService;

    @Value("${security.jwt.refresh-expiration-time}")
    private long refreshTokenDurationMs;

    public RefreshToken createRefreshToken(String email){
        logger.info("Create refresh token request");

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setCreatedAt(Instant.now());
        refreshToken.setExpiresAt(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshTokenRepo.save(refreshToken);

        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiresAt().isBefore(Instant.now())){
            refreshTokenRepo.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }
        return token;
    }

    public void revokeRefreshToken(UUID refreshTokenId){
        refreshTokenRepo.deleteById(refreshTokenId);
    }

    public LoginResponseDTO refreshAccessToken(UUID refreshTokenId){
        logger.info("Refreshing access token");

        RefreshToken refreshToken = refreshTokenRepo.findById(refreshTokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource token not found"));

        verifyExpiration(refreshToken);

        String newAccessToken = jwtService.generateToken(
                userService.loadUserByUsername(refreshToken.getUser().getEmail())
        );

        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setToken(newAccessToken);
        dto.setExpiresIn(jwtService.getExpirationTime());
        dto.setRefreshToken(refreshTokenId);

        return dto;
    }


}
