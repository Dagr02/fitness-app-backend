package com.example.fitness_app_backend.repository;

import com.example.fitness_app_backend.model.ProgramExercise;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramExerciseRepo extends JpaRepository<ProgramExercise, Long> {
    List<ProgramExercise> findByProgramId(Long programId);
}
