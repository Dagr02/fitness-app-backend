package com.example.fitness_app_backend.dto.programs;

import com.example.fitness_app_backend.model.UserExerciseLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data

public class UserExerciseLogDTO {
    private Long programExerciseId;
    private String exerciseName;
    private Integer setNumber;
    private Integer completedReps;
    private Double weightUsed;
    private LocalDateTime workoutDate;

    public UserExerciseLogDTO(UserExerciseLog log){
        this.programExerciseId = log.getProgramExercise().getId();
        this.exerciseName = log.getProgramExercise().getExercise().getName();
        this.setNumber = log.getSetNumber();
        this.completedReps = log.getCompletedReps();
        this.weightUsed = log.getWeightUsed();
        this.workoutDate = log.getWorkoutDate();
    }
}
