package com.example.fitness_app_backend.controller;

import com.example.fitness_app_backend.dto.CreateProgramDTO;
import com.example.fitness_app_backend.model.UserProgram;
import com.example.fitness_app_backend.service.ProgramService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/programs")
public class ProgramController {
    private final ProgramService programService;

    @PostMapping("/custom")
    public ResponseEntity<?> createCustomProgram(@RequestBody CreateProgramDTO dto){
        UserProgram userProgram = programService.createCustomProgram(dto);
        return ResponseEntity.ok(userProgram);
    }
}
