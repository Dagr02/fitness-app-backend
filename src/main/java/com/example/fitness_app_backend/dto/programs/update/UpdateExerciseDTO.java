package com.example.fitness_app_backend.dto.programs.update;

import lombok.Data;

@Data
public class UpdateExerciseDTO {
    private Long programExerciseId;
    private Long exerciseId;
    private Integer sets;
    private Integer reps;
    private Double weight;
}
