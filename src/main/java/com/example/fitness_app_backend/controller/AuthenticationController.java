package com.example.fitness_app_backend.controller;

import com.example.fitness_app_backend.dto.auth.*;
import com.example.fitness_app_backend.model.RefreshToken;
import com.example.fitness_app_backend.service.AuthenticationService;
import com.example.fitness_app_backend.service.JwtService;
import com.example.fitness_app_backend.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@AllArgsConstructor
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDTO> register(@RequestBody @Valid
                                           RegistrationRequestDTO request){
        String token = authenticationService.register(request);
        return ResponseEntity.ok(new RegistrationResponseDTO(
                token, "Registration successful. Please confirm your email."
        ));
    }

    @GetMapping("/registration/confirmation")
    public ResponseEntity<String> confirm(@RequestParam("token") String token){
        return ResponseEntity.ok(authenticationService.confirmToken(token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginRequestDTO loginRequestDTO){
        UserDetails authenticatedUser = authenticationService.authenticate(loginRequestDTO);

        //jwt access token
        String jwtToken = jwtService.generateToken(authenticatedUser);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authenticatedUser.getUsername());


        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken(jwtToken);
        loginResponseDTO.setExpiresIn(jwtService.getExpirationTime());
        loginResponseDTO.setRefreshToken(refreshToken.getId());

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO request){
        logger.info("New jwt access token request");
        LoginResponseDTO dto = refreshTokenService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody UUID refreshToken){
        refreshTokenService.revokeRefreshToken(refreshToken);
        return ResponseEntity.noContent().build();
    }

}
