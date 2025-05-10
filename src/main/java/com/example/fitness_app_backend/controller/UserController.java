package com.example.fitness_app_backend.controller;

import com.example.fitness_app_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/program")
    public ResponseEntity<?> getUserProgram(){
        return ResponseEntity.ok(userService.getUserProgram());
    }

}
