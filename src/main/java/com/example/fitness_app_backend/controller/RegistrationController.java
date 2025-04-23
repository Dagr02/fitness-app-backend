package com.example.fitness_app_backend.controller;

import com.example.fitness_app_backend.dto.RegistrationRequestDTO;
import com.example.fitness_app_backend.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid
                                           RegistrationRequestDTO request){
        return ResponseEntity.ok(registrationService.register(request));
    }

    @GetMapping("/confirmation")
    public ResponseEntity<String> confirm(@RequestParam("token") String token){
        return ResponseEntity.ok(registrationService.confirmToken(token));
    }

}
