package com.example.fitness_app_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkoutLogDTO {
    private List<ExerciseStatsDTO> stats;
}
