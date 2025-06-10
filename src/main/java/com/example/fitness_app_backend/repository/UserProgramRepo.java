package com.example.fitness_app_backend.repository;

import com.example.fitness_app_backend.model.UserProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProgramRepo extends JpaRepository<UserProgram, Long> {

    Optional<UserProgram> findByUserId(Long userId);

    Optional<UserProgram> findByUserIdAndProgramId(Long userId, Long programId);

}
