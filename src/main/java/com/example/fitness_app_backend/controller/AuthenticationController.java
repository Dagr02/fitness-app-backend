package com.example.fitness_app_backend.controller;

import com.example.fitness_app_backend.dto.auth.LoginRequestDTO;
import com.example.fitness_app_backend.dto.auth.LoginResponseDTO;
import com.example.fitness_app_backend.dto.auth.RegistrationRequestDTO;
import com.example.fitness_app_backend.dto.auth.RegistrationResponseDTO;
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

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken(jwtToken);
        loginResponseDTO.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponseDTO);
    }

}
