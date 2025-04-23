package com.example.fitness_app_backend.dto;

import lombok.Data;

@Data
public class ProgramExerciseDTO {
    private Long exerciseId;
    private int sets;
    private int reps;
    private int orderIndex;
}
