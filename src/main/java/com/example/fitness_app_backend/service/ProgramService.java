package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.dto.programs.CreateProgramDTO;
import com.example.fitness_app_backend.dto.programs.CreateProgramExerciseDTO;
import com.example.fitness_app_backend.model.*;
import com.example.fitness_app_backend.repository.ExerciseRepo;
import com.example.fitness_app_backend.repository.ProgramExerciseRepo;
import com.example.fitness_app_backend.repository.ProgramRepo;
import com.example.fitness_app_backend.repository.UserProgramRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProgramService {
    private final ProgramRepo programRepo;
    private final ProgramExerciseRepo programExerciseRepo;
    private final ExerciseRepo exerciseRepo;
    private final UserProgramRepo userProgramRepo;
    private final UserService userService;

    @Transactional
    public UserProgram createCustomProgram(@RequestBody CreateProgramDTO createProgramDTO){
        //get current user
        User user = userService.getCurrentUser();

        // create program
        Program program = new Program();
        program.setName(createProgramDTO.getProgram().getName());
        program.setDescription(createProgramDTO.getProgram().getDescription());
        program.setCreatedAt(LocalDateTime.now());
        program.setStartDate(createProgramDTO.getProgram().getStartDate());
        program.setEndDate(createProgramDTO.getProgram().getEndDate());

        programRepo.save(program);

        //create program exercise for each exercise
        List<CreateProgramExerciseDTO> exercisesDTO = createProgramDTO.getExercises();
        List<ProgramExercise> pe = new ArrayList<>();
        for(CreateProgramExerciseDTO item : exercisesDTO){
            ProgramExercise programExercise = new ProgramExercise();
            programExercise.setProgram(program);

            Exercise exercise = exerciseRepo.findById(item.getExerciseId()).orElseThrow(() -> new RuntimeException("Exercise not found"));
            programExercise.setExercise(exercise);
            programExercise.setReps(item.getReps());
            programExercise.setSets(item.getSets());
            programExercise.setOrderIndex(item.getOrderIndex());

            pe.add(programExercise);
        }
        programExerciseRepo.saveAll(pe);

        //create user program
        UserProgram userProgram = new UserProgram();
        userProgram.setProgramDate(LocalDateTime.now());
        userProgram.setUser(user);
        userProgram.setProgram(program);

        return userProgramRepo.save(userProgram);
    }
}
