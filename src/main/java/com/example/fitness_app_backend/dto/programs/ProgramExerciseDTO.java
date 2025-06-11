package com.example.fitness_app_backend.dto.programs;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProgramExerciseDTO {
    private Long exerciseId;
    private Long programExerciseId;
    private String exerciseName;
    private int sets;
    private int reps;
    private Double weight;
    private int orderIndex;
    private int dayNumber;

    private List<ExerciseLogDTO> logs;
}
