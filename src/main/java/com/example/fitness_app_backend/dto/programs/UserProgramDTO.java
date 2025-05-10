package com.example.fitness_app_backend.dto.programs;

import lombok.Data;

import java.util.List;

@Data
public class UserProgramDTO {
    private ProgramDTO program;
    private List<ProgramExerciseDTO> exercises;
}
