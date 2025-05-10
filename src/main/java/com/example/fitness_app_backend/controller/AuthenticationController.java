package com.example.fitness_app_backend.controller;

import com.example.fitness_app_backend.dto.LoginRequestDTO;
import com.example.fitness_app_backend.dto.LoginResponseDTO;
import com.example.fitness_app_backend.dto.RegistrationRequestDTO;
import com.example.fitness_app_backend.model.User;
import com.example.fitness_app_backend.service.AuthenticationService;
import com.example.fitness_app_backend.service.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final JwtService jwtService;

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody @Valid
                                           RegistrationRequestDTO request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @GetMapping("/registration/confirmation")
    public ResponseEntity<String> confirm(@RequestParam("token") String token){
        return ResponseEntity.ok(authenticationService.confirmToken(token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginRequestDTO loginRequestDTO){
        UserDetails authenticatedUser = authenticationService.authenticate(loginRequestDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken(jwtToken);
        loginResponseDTO.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponseDTO);
    }

}
