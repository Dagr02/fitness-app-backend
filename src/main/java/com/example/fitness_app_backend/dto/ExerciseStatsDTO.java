package com.example.fitness_app_backend.dto;

import lombok.Data;

@Data
public class ExerciseStatsDTO {
    private Long exerciseId;
    private int sets;
    private int reps;
    private double weight;
}
