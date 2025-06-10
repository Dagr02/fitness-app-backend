package com.example.fitness_app_backend.dto.programs;

import lombok.Data;

import java.util.List;

@Data
public class CreateProgramDTO {
    private ProgramDTO program;
    private List<CreateProgramExerciseDTO> exercises;
}
