package com.example.fitness_app_backend.dto.programs;

import lombok.Data;

import java.util.List;

@Data
public class ProgramDayDTO {
    private int DayNumber;
    private List<ProgramExerciseDTO> exercises;
}
