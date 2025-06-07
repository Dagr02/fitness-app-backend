package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.dto.programs.*;
import com.example.fitness_app_backend.mapper.ProgramMapper;
import com.example.fitness_app_backend.model.*;
import com.example.fitness_app_backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProgramService {
    private final ProgramRepo programRepo;
    private final ProgramExerciseRepo programExerciseRepo;
    private final ExerciseRepo exerciseRepo;
    private final UserProgramRepo userProgramRepo;
    private final UserService userService;
    private final UserExerciseLogRepo userExerciseLogRepo;
    private final ProgramMapper programMapper;

    @Transactional
    public UserProgramDTO createCustomProgram(@RequestBody CreateProgramDTO createProgramDTO){
        //get current user
        User user = userService.getCurrentUser();

        // create program
        Program program = new Program();
        program.setName(createProgramDTO.getName());
        program.setDescription(createProgramDTO.getDescription());
        program.setCreatedAt(LocalDateTime.now());
        program.setStartDate(createProgramDTO.getStartDate());
        program.setEndDate(createProgramDTO.getEndDate());

        programRepo.save(program);

        for(CreateProgramDayDTO dayDTO : createProgramDTO.getWorkouts()){
            int orderIndex = 0;

            for(CreateExerciseDTO exerciseDTO : dayDTO.getExercises()){
                Exercise exercise = exerciseRepo.findById(exerciseDTO.getExerciseId())
                        .orElseThrow(() -> new RuntimeException("Exercises not found when creating custom program."));

                ProgramExercise programExercise = new ProgramExercise();
                programExercise.setProgram(program);
                programExercise.setExercise(exercise);
                programExercise.setDayNumber(dayDTO.getDay());
                programExercise.setOrderIndex(orderIndex++);
                programExercise.setSets(exerciseDTO.getSets());
                programExercise.setReps(exerciseDTO.getReps());

                programExerciseRepo.save(programExercise);
            }
        }


        //create user program
        UserProgram userProgram = new UserProgram();
        userProgram.setProgramDate(program.getCreatedAt());
        userProgram.setUser(user);
        userProgram.setProgram(program);

        userProgramRepo.save(userProgram);

        //return new object for context
        List<ProgramExercise> programExercises = programExerciseRepo.findByProgramId(program.getId());
        List<UserExerciseLog> logs = userExerciseLogRepo.findByUserIdAndProgramId(user.getId(), program.getId());
        return programMapper.toUserProgramDTO(program, programExercises, logs);
    }
}
