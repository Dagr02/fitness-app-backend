package com.example.fitness_app_backend.dto.programs;

import lombok.Data;

@Data
public class ProgramExerciseDTO {
    private Long exerciseId;
    private Long programId;
    private int sets;
    private int reps;
    private int orderIndex;
    private int dayNumber;
}
