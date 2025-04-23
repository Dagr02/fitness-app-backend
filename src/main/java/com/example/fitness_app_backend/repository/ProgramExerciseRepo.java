package com.example.fitness_app_backend.repository;

import com.example.fitness_app_backend.model.ProgramExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramExerciseRepo extends JpaRepository<ProgramExercise, Long> {

}
