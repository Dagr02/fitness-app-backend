package com.example.fitness_app_backend.dto.programs.update;

import lombok.Data;

import java.util.List;

@Data
public class UpdateProgramDayDTO {
    private int day;
    private List<UpdateExerciseDTO> exercises;
}
