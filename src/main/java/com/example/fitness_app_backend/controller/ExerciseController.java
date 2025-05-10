package com.example.fitness_app_backend.controller;


import com.example.fitness_app_backend.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<?> getAllExercises(){
        System.out.println("Request Received");
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

}
