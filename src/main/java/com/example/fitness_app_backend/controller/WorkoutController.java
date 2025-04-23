package com.example.fitness_app_backend.controller;


import com.example.fitness_app_backend.dto.WorkoutLogDTO;
import com.example.fitness_app_backend.model.WorkoutSession;
import com.example.fitness_app_backend.service.WorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workouts")
@AllArgsConstructor
public class WorkoutController {
    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<WorkoutSession> logWorkout(
            @RequestBody WorkoutLogDTO workoutLogDTO
    ){
        return ResponseEntity.ok(
                workoutService.logWorkout(workoutLogDTO.getStats())
        );
    }

}
