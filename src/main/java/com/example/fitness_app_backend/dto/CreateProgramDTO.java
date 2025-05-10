package com.example.fitness_app_backend.dto;

import com.example.fitness_app_backend.dto.programs.CreateProgramExerciseDTO;
import com.example.fitness_app_backend.dto.programs.ProgramDTO;
import lombok.Data;

import java.util.List;

@Data
public class CreateProgramDTO {
    private ProgramDTO program;
    private List<CreateProgramExerciseDTO> exercises;
}
