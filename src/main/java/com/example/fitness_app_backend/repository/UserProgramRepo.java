package com.example.fitness_app_backend.repository;

import com.example.fitness_app_backend.model.UserProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProgramRepo extends JpaRepository<UserProgram, Long> {

    Optional<UserProgram> findByUserId(UUID userId);

    Optional<UserProgram> findByUserIdAndProgramId(UUID userId, Long programId);

}
