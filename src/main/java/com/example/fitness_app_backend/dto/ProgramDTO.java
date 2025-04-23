package com.example.fitness_app_backend.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProgramDTO {
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
