package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.dto.ProgramDTO;
import com.example.fitness_app_backend.dto.ProgramExerciseDTO;
import com.example.fitness_app_backend.model.Exercise;
import com.example.fitness_app_backend.model.Program;
import com.example.fitness_app_backend.model.ProgramExercise;
import com.example.fitness_app_backend.model.User;
import com.example.fitness_app_backend.repository.ExerciseRepo;
import com.example.fitness_app_backend.repository.ProgramExerciseRepo;
import com.example.fitness_app_backend.repository.ProgramRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProgramService {
    private final ProgramRepo programRepo;
    private final ProgramExerciseRepo programExRepo;
    private final ExerciseRepo exerciseRepo;
    private final UserService userService;

    public Program createProgram(ProgramDTO dto) {
        Program program = new Program();
        program.setName(dto.getName());
        program.setDescription(dto.getDescription());
        program.setStartDate(dto.getStartDate());
        program.setEndDate(dto.getEndDate());
        program.setUser(userService.getCurrentUser());
        program.setCreatedAt(LocalDateTime.now());
        return programRepo.save(program);
    }

    public void addExerciseToProgram(Long programId, ProgramExerciseDTO dto) {
        Program program = programRepo.findById(programId).orElseThrow();
        Exercise exercise = exerciseRepo.findById(dto.getExerciseId()).orElseThrow();

        ProgramExercise pe = new ProgramExercise();
        pe.setProgram(program);
        pe.setExercise(exercise);
        pe.setSets(dto.getSets());
        pe.setReps(dto.getReps());
        pe.setOrderIndex(dto.getOrderIndex());

        programExRepo.save(pe);
    }

}
