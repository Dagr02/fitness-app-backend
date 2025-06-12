package com.example.fitness_app_backend.dto.programs.create;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateExerciseLogDTO {
    private Long programExerciseId;
    private Integer setNumber;
    private Integer completedReps;
    private Double weightUsed;
    private LocalDateTime workoutDate;
}
