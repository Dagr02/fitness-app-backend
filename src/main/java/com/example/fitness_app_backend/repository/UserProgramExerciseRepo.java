package com.example.fitness_app_backend.repository;

import com.example.fitness_app_backend.model.UserProgramExercises;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProgramExerciseRepo extends JpaRepository<UserProgramExercises, Long> {

    @Transactional
    @Query("SELECT upe FROM UserProgramExercises upe " +
            "WHERE upe.userProgram.user.id = ?1"

    )
    UserProgramExercises getUserPrograms(Long userId);
}
