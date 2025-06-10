package com.example.fitness_app_backend.controller;


import com.example.fitness_app_backend.dto.exercises.CreateExerciseDTO;
import com.example.fitness_app_backend.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/exercises")
public class ExerciseController {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);
    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<?> getAllExercises(){
       logger.info("Fetching all exercises");
       return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @PostMapping
    public ResponseEntity<?> createExercise(@RequestBody CreateExerciseDTO dto){
        logger.info("Create Exercise request");
        return ResponseEntity.ok(exerciseService.createExercise(dto));

    }

}
