package com.example.fitness_app_backend.service;


import com.example.fitness_app_backend.dto.programs.ExerciseDTO;
import com.example.fitness_app_backend.model.Exercise;
import com.example.fitness_app_backend.repository.ExerciseRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExerciseService {
    private final static Logger logger = LoggerFactory.getLogger(ExerciseService.class);
    private final ExerciseRepo exerciseRepo;

    @GetMapping
    public List<ExerciseDTO> getAllExercises(){
        logger.info("Getting all exercises");
        return exerciseRepo.findAll().stream()
                .map(ex -> new ExerciseDTO(
                        ex.getId(),
                        ex.getName(),
                        ex.getDescription()
                ))
                .collect(Collectors.toList());
    }


}
