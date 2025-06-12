package com.example.fitness_app_backend.controller;

import com.example.fitness_app_backend.dto.programs.create.CreateExerciseLogDTO;
import com.example.fitness_app_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/program")
    public ResponseEntity<?> getUserProgram(){
        return ResponseEntity.ok(userService.getUserProgram());
    }

    @PostMapping("/log-workout")
    public ResponseEntity<?> saveExerciseLogs(@RequestBody List<CreateExerciseLogDTO> logs){
        userService.saveExerciseLogs(logs);
        return ResponseEntity.ok().build();
    }

}
