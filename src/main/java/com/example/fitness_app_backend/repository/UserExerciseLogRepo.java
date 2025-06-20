package com.example.fitness_app_backend.repository;

import com.example.fitness_app_backend.model.User;
import com.example.fitness_app_backend.model.UserExerciseLog;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserExerciseLogRepo extends JpaRepository<UserExerciseLog, Long> {

    @Query("""
        SELECT uel FROM UserExerciseLog uel
        WHERE uel.user.id = :userId
          AND uel.programExercise.program.id = :programId
    """)
    List<UserExerciseLog> findByUserIdAndProgramId(@Param("userId") UUID userId,
                                                   @Param("programId") Long programId);


    List<UserExerciseLog> findByUserOrderByWorkoutDateAsc(User user);

    List<UserExerciseLog> findByUserAndProgramExercise_Exercise_IdOrderByWorkoutDateAsc(User user, Long exerciseId);
}
