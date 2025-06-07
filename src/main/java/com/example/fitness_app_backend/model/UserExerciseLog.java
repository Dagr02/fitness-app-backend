package com.example.fitness_app_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_exercise_log")
public class UserExerciseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "program_exercise_id")
    private ProgramExercise programExercise;

    private Integer setNumber;
    private Integer completedReps;
    private Double weightUsed;
    private LocalDateTime workoutDate;
}
