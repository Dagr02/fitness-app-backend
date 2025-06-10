package com.example.fitness_app_backend.dto.exercises;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    private Long id;
    private String name;
    private String description;
}

