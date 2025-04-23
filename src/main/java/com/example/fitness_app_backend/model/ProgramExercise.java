package com.example.fitness_app_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "program_exercise")
public class ProgramExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private int sets;
    private int reps;
    private int orderIndex;
}
