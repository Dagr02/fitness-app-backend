package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.dto.programs.*;
import com.example.fitness_app_backend.exceptions.ResourceNotFoundException;
import com.example.fitness_app_backend.exceptions.auth.UserAlreadyExistsException;
import com.example.fitness_app_backend.mapper.ProgramMapper;
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
    private final ProgramMapper programMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }
    public String signUpUser(User user){
        boolean userExists = userRepository.findByEmail((user.getEmail())).isPresent();
        if(userExists){
            throw new UserAlreadyExistsException("User with this email already exists.");
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
                .orElseThrow(() -> new ResourceNotFoundException("No program found for user"));

        Program program = userProgram.getProgram();
        List<ProgramExercise> programExercises = programExerciseRepo.findByProgramId(program.getId());

        List<UserExerciseLog> logs = userExerciseLogRepo.findByUserIdAndProgramId(user.getId(), program.getId());

        return programMapper.toUserProgramDTO(program, programExercises, logs);
    }

    public void saveExerciseLogs(List<CreateExerciseLogDTO> logs) {
        User user = getCurrentUser();

        List<UserExerciseLog> entities = logs.stream()
                .map(dto -> {
                    UserExerciseLog log = new UserExerciseLog();
                    log.setUser(user);
                    log.setProgramExercise(programExerciseRepo.findById(dto.getProgramExerciseId()).orElseThrow());
                    log.setCompletedReps(dto.getCompletedReps());
                    log.setWeightUsed(dto.getWeightUsed());
                    log.setWorkoutDate(dto.getWorkoutDate());
                    log.setSetNumber(dto.getSetNumber());
                    return log;
                })
                .toList();

        userExerciseLogRepo.saveAll(entities);
    }
}
