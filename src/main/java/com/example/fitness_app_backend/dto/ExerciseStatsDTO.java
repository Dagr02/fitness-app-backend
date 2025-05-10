package com.example.fitness_app_backend.dto;

import lombok.Data;

@Data
public class ExerciseStatsDTO {
    private ExerciseDTO exercise;
    private int sets;
    private int reps;
    private double weight;
}
