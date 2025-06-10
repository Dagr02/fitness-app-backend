package com.example.fitness_app_backend.repository;

import com.example.fitness_app_backend.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepo extends JpaRepository<Exercise, Long> {

    @Query("SELECT e FROM Exercise e WHERE e.user IS NULL OR e.user.id = :userId")
    List<Exercise> findGlobalOrUserExercises(@Param("userId") Long userId);

}
