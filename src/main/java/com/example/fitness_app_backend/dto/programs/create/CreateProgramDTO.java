package com.example.fitness_app_backend.dto.programs.create;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateProgramDTO {
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<CreateProgramDayDTO> workouts;
}
