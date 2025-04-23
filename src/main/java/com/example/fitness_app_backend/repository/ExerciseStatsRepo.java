package com.example.fitness_app_backend.repository;

import com.example.fitness_app_backend.model.ExerciseStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseStatsRepo extends JpaRepository<ExerciseStats, Long> {
}
