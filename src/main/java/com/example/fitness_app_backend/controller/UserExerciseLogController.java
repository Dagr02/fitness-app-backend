package com.example.fitness_app_backend.controller;

import com.example.fitness_app_backend.dto.programs.UserExerciseLogDTO;
import com.example.fitness_app_backend.service.UserExerciseLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercise-logs")
@RequiredArgsConstructor
public class UserExerciseLogController {
    private final UserExerciseLogService userExerciseLogService;

    @GetMapping("/user")
    public ResponseEntity<List<UserExerciseLogDTO>> getUserExerciseLogs() {
        List<UserExerciseLogDTO> logs = userExerciseLogService.getUserExerciseLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/user/{exerciseId}")
    public ResponseEntity<List<UserExerciseLogDTO>> getUserExerciseLogsByExercise(@PathVariable Long exerciseId) {
        List<UserExerciseLogDTO> logs = userExerciseLogService.getUserExerciseLogsByExercise(exerciseId);
        return ResponseEntity.ok(logs);
    }
}
