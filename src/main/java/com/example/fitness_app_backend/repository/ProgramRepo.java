package com.example.fitness_app_backend.repository;

import com.example.fitness_app_backend.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepo extends JpaRepository<Program, Long> {
}
