package com.example.fitness_app_backend.dto.programs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProgramExerciseDTO {
    private Long exerciseId;
    private Long programExerciseId;
    private String exerciseName;
    private int sets;
    private int reps;
    private int orderIndex;
    private int dayNumber;

    private Integer completedSets;
    private Integer completedReps;
    private Double weightUsed;
    private LocalDateTime workoutDate;
}
