package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.model.ExerciseStats;
import com.example.fitness_app_backend.repository.ExerciseStatsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExerciseStatsService {
    private final ExerciseStatsRepo exerciseStatsRepo;

    public List<ExerciseStats> getAllExerciseStats(){ return exerciseStatsRepo.findAll();}

    public ExerciseStats getExerciseStatsById(Long id){
        Optional<ExerciseStats> optionalExerciseStats = exerciseStatsRepo.findById(id);

        if(optionalExerciseStats.isPresent()){
            return optionalExerciseStats.get();
        }

        log.info("Exercise stats with id: {} doesn't exist", id);
        return null;
    }

    public ExerciseStats saveExerciseStats(ExerciseStats exerciseStats){
        exerciseStats.setCreatedAt(LocalDateTime.now());
        exerciseStats.setUpdatedAt(LocalDateTime.now());
        ExerciseStats savedExerciseStats = exerciseStatsRepo.save(exerciseStats);

        log.info("Exercise stats with id: {} saved successfully", exerciseStats.getId());
        return savedExerciseStats;
    }

    public ExerciseStats updateExerciseStats(ExerciseStats exerciseStats){
        Optional<ExerciseStats> existingExerciseStats = exerciseStatsRepo.findById(exerciseStats.getId());
        exerciseStats.setCreatedAt(existingExerciseStats.get().getCreatedAt());
        exerciseStats.setUpdatedAt(LocalDateTime.now());

        ExerciseStats updatedExerciseStats = exerciseStatsRepo.save(exerciseStats);

        log.info("Exercise stats with id: {} updated successfully", exerciseStats.getId());
        return updatedExerciseStats;
    }

    public void deleteExerciseStatsById(Long id){
        exerciseStatsRepo.deleteById(id);
    }
}
