package com.example.fitness_app_backend.repository;

import com.example.fitness_app_backend.model.Exercise;
import com.example.fitness_app_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepo extends JpaRepository<Exercise, Long> {

}
