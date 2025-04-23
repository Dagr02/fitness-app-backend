package com.example.fitness_app_backend.controller;

import com.example.fitness_app_backend.dto.ProgramDTO;
import com.example.fitness_app_backend.dto.ProgramExerciseDTO;
import com.example.fitness_app_backend.model.Program;
import com.example.fitness_app_backend.service.ProgramService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/programs")
@AllArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @PostMapping
    public ResponseEntity<Program> create(@RequestBody ProgramDTO programDTO){
        return ResponseEntity.ok(programService.createProgram(programDTO));
    }

    @PostMapping("/{id}/exercises")
    public ResponseEntity<?> addExercise(@PathVariable Long id, @RequestBody ProgramExerciseDTO programExerciseDTO){
        programService.addExerciseToProgram(id, programExerciseDTO);
        return ResponseEntity.ok().build();
    }


}
