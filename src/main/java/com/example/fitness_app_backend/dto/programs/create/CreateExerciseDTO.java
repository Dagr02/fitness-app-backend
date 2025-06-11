package com.example.fitness_app_backend.dto.programs.create;

import lombok.Data;

@Data
public class CreateExerciseDTO {
    private Long exerciseId;
    private Integer sets;
    private Integer reps;
    private Double weight;
}
