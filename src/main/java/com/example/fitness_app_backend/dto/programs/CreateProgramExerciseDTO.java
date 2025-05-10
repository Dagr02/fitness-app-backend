package com.example.fitness_app_backend.dto.programs;

import lombok.Data;

@Data
public class CreateProgramExerciseDTO {
    private Long exerciseId;
    private int sets;
    private int reps;
    private int orderIndex;
    private int dayNumber;
}
