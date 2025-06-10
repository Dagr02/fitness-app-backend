package com.example.fitness_app_backend.dto.programs;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class ExerciseLogDTO {
    private int setNumber;
    private Integer completedReps;
    private Double weightUsed;
    private LocalDateTime workoutDate;
}
