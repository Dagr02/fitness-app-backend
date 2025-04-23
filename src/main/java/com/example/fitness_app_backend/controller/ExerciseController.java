package com.example.fitness_app_backend.controller;

import com.example.fitness_app_backend.dto.ExerciseDTO;
import com.example.fitness_app_backend.model.Exercise;
import com.example.fitness_app_backend.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@AllArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<Exercise> create(@RequestBody ExerciseDTO dto){
        return ResponseEntity.ok(exerciseService.createUserExercise(dto));
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllAvailable(){
        return ResponseEntity.ok(exerciseService.getAvailableExercises());
    }

}
