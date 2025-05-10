package com.example.fitness_app_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_program_exercises")
public class UserProgramExercises {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_program_id")
    private UserProgram userProgram;

    @ManyToOne
    @JoinColumn(name = "program_exercise_id")
    private ProgramExercise programExercise;

    private Integer completedSets;
    private Integer completedReps;
    private Double weightUsed;
    private LocalDateTime workoutDate;
}
