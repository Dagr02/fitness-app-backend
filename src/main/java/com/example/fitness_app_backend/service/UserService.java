package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.dto.programs.ProgramDTO;
import com.example.fitness_app_backend.dto.programs.ProgramDayDTO;
import com.example.fitness_app_backend.dto.programs.ProgramExerciseDTO;
import com.example.fitness_app_backend.dto.programs.UserProgramDTO;
import com.example.fitness_app_backend.model.*;
import com.example.fitness_app_backend.repository.ProgramExerciseRepo;
import com.example.fitness_app_backend.repository.UserExerciseLogRepo;
import com.example.fitness_app_backend.repository.UserProgramRepo;
import com.example.fitness_app_backend.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepo userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final UserExerciseLogRepo userExerciseLogRepo;
    private final UserProgramRepo userProgramRepo;
    private final ProgramExerciseRepo programExerciseRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }
    public String signUpUser(User user){
        boolean userExists = userRepository.findByEmail((user.getEmail())).isPresent();
        if(userExists){
            throw new IllegalStateException("User already exists");
        }

        // Create an encrypted password.
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        Token confirmationToken = new Token(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                user
        );

        tokenService.saveConfirmationToken(confirmationToken);
        return token;
    }
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    public UserProgramDTO getUserProgram(){
        User user = getCurrentUser();

        UserProgram userProgram = userProgramRepo.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("No program found for user"));

        Program program = userProgram.getProgram();
        List<ProgramExercise> programExercises = programExerciseRepo.findByProgramId(program.getId());

        List<UserExerciseLog> logs = userExerciseLogRepo.findByUserIdAndProgramId(user.getId(), program.getId());

        Map<Long, UserExerciseLog> logMap = logs.stream()
                .collect(Collectors.toMap(
                   log -> log.getProgramExercise().getId(),
                   log -> log
                ));

        Map<Integer, List<ProgramExerciseDTO>> daysMap = new TreeMap<>();

        for(ProgramExercise pe : programExercises){
            ProgramExerciseDTO dto = new ProgramExerciseDTO();
            dto.setProgramExerciseId(pe.getId());
            dto.setExerciseId(pe.getExercise().getId());
            dto.setExerciseName(pe.getExercise().getName());
            dto.setSets(pe.getSets());
            dto.setReps(pe.getReps());
            dto.setOrderIndex(pe.getOrderIndex());
            dto.setDayNumber(pe.getDayNumber());

            UserExerciseLog log = logMap.get(pe.getId());
            if(log != null){
                dto.setCompletedSets(log.getCompletedSets());
                dto.setCompletedReps(log.getCompletedReps());
                dto.setWeightUsed(log.getWeightUsed());
                dto.setWorkoutDate(log.getWorkoutDate());
            }

            daysMap.computeIfAbsent(pe.getDayNumber(), d -> new ArrayList<>()).add(dto);
        }

        List<ProgramDayDTO> programDays = daysMap.entrySet().stream()
                .map(entry -> {
                    ProgramDayDTO day = new ProgramDayDTO();
                    day.setDayNumber(entry.getKey());
                    day.setExercises(entry.getValue());
                    return day;
                }).toList();

        int currentDay = logs.stream()
                .map(log -> log.getProgramExercise().getDayNumber())
                .max(Integer::compareTo)
                .orElse(0) + 1;

        currentDay = Math.min(currentDay, daysMap.keySet().stream().max(Integer::compareTo).orElse(1));

        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setName(program.getName());
        programDTO.setDescription(program.getDescription());
        programDTO.setStartDate(program.getStartDate());
        programDTO.setEndDate(program.getEndDate());

        UserProgramDTO userProgramDTO = new UserProgramDTO();
        userProgramDTO.setProgram(programDTO);
        userProgramDTO.setDays(programDays);
        userProgramDTO.setCurrentDay(currentDay);

        return userProgramDTO;
    }
}
