package com.example.fitness_app_backend.dto.programs.update;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateProgramDTO {
    private Long programId; // required to identify program
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<UpdateProgramDayDTO> workouts;
}
