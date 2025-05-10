package com.example.fitness_app_backend.service;


import com.example.fitness_app_backend.model.Exercise;
import com.example.fitness_app_backend.repository.ExerciseRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseService {
    private final static Logger logger = LoggerFactory.getLogger(ExerciseService.class);
    private final ExerciseRepo exerciseRepo;

    @GetMapping
    public List<Exercise> getAllExercises(){
        logger.info("Getting all exercises");
        return exerciseRepo.findAll();
    }


}
