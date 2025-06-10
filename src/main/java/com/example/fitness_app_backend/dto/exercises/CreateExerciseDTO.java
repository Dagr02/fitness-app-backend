package com.example.fitness_app_backend.dto.exercises;

import lombok.Data;

@Data
public class CreateExerciseDTO {
    private String name;
    private String description;
}
