package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.dto.programs.*;
import com.example.fitness_app_backend.dto.programs.create.CreateExerciseDTO;
import com.example.fitness_app_backend.dto.programs.create.CreateProgramDTO;
import com.example.fitness_app_backend.dto.programs.create.CreateProgramDayDTO;
import com.example.fitness_app_backend.dto.programs.update.UpdateExerciseDTO;
import com.example.fitness_app_backend.dto.programs.update.UpdateProgramDTO;
import com.example.fitness_app_backend.dto.programs.update.UpdateProgramDayDTO;
import com.example.fitness_app_backend.exceptions.ResourceNotFoundException;
import com.example.fitness_app_backend.mapper.ProgramMapper;
import com.example.fitness_app_backend.model.*;
import com.example.fitness_app_backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDateTime;
import java.util.List;


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
    public UserProgramDTO createCustomProgram(CreateProgramDTO createProgramDTO){
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

        //return new object
        List<ProgramExercise> programExercises = programExerciseRepo.findByProgramId(program.getId());
        List<UserExerciseLog> logs = userExerciseLogRepo.findByUserIdAndProgramId(user.getId(), program.getId());
        return programMapper.toUserProgramDTO(program, programExercises, logs);
    }

    @Transactional
    public UserProgramDTO updateCustomProgram(UpdateProgramDTO dto){
        User user = userService.getCurrentUser();

        Program program = programRepo.findById(dto.getProgramId())
                .orElseThrow( () -> new ResourceNotFoundException("Program not found"));

        // verify program is owned by user
        UserProgram userProgram = userProgramRepo.findByUserIdAndProgramId(user.getId(), dto.getProgramId())
                .orElseThrow(() -> new AccessDeniedException("You don't have permission to delete this program"));

        program.setName(dto.getName());
        program.setDescription(dto.getDescription());
        program.setStartDate(dto.getStartDate());
        program.setEndDate(dto.getEndDate());
        program.setUpdatedAt(LocalDateTime.now());

        programRepo.save(program);

        programExerciseRepo.deleteByProgramId(program.getId());

        int orderIndex = 0;

        for(UpdateProgramDayDTO dayDTO : dto.getWorkouts()){
            for(UpdateExerciseDTO exDTO : dayDTO.getExercises()){
                Exercise exercise = exerciseRepo.findById(exDTO.getExerciseId())
                        .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));

                ProgramExercise pe = new ProgramExercise();
                pe.setProgram(program);
                pe.setExercise(exercise);
                pe.setDayNumber(dayDTO.getDay());
                pe.setOrderIndex(orderIndex++);
                pe.setSets(exDTO.getSets());
                pe.setReps(exDTO.getReps());

                programExerciseRepo.save(pe);
            }
        }

        List<ProgramExercise> programExercises = programExerciseRepo.findByProgramId(program.getId());
        List<UserExerciseLog> logs = userExerciseLogRepo.findByUserIdAndProgramId(user.getId(), program.getId());

        return programMapper.toUserProgramDTO(program, programExercises, logs);

    }

    @Transactional
    public void deleteProgram(Long programId){
        User user = userService.getCurrentUser();

        UserProgram userProgram = userProgramRepo.findByUserIdAndProgramId(user.getId(), programId)
                .orElseThrow(() -> new AccessDeniedException("You don't have permission to delete this program"));

        programExerciseRepo.deleteByProgramId(programId);
        userProgramRepo.delete(userProgram);
        programRepo.deleteById(programId);
    }
}
