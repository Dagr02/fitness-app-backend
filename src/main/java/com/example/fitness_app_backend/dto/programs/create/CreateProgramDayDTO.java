package com.example.fitness_app_backend.dto.programs.create;


import lombok.Data;

import java.util.List;

@Data
public class CreateProgramDayDTO {
    private int day;
    private List<CreateExerciseDTO> exercises;

}
