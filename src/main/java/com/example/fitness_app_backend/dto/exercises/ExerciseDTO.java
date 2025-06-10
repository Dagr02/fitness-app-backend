package com.example.fitness_app_backend.dto.exercises;

import lombok.Data;

@Data
public class ExerciseDTO {
    private Long id;
    private String name;
    private String description;
}
