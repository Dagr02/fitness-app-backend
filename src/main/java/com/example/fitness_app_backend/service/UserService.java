package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.dto.programs.ProgramDTO;
import com.example.fitness_app_backend.dto.programs.ProgramExerciseDTO;
import com.example.fitness_app_backend.dto.programs.UserProgramDTO;
import com.example.fitness_app_backend.model.ProgramExercise;
import com.example.fitness_app_backend.model.Token;
import com.example.fitness_app_backend.model.User;
import com.example.fitness_app_backend.model.UserProgramExercises;
import com.example.fitness_app_backend.repository.ProgramExerciseRepo;
import com.example.fitness_app_backend.repository.UserProgramExerciseRepo;
import com.example.fitness_app_backend.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepo userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final UserProgramExerciseRepo userProgramExerciseRepo;
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

    public Long getCurrentUserId(){
        return getCurrentUser().getId();
    }

    public UserProgramDTO getUserProgram(){
        User user = getCurrentUser();
        logger.info("Fetching user program for user id {} ", user.getId());

        UserProgramExercises userProgramExercises = userProgramExerciseRepo.getUserPrograms(user.getId());

        if (userProgramExercises == null || userProgramExercises.getUserProgram() == null || userProgramExercises.getUserProgram().getProgram() == null) {
            throw new RuntimeException("No user program found for user with ID: " + user.getId());
        }

        UserProgramDTO userProgramDTO = new UserProgramDTO();
        ProgramDTO programDTO = new ProgramDTO();

        programDTO.setName(userProgramExercises.getUserProgram().getProgram().getName());
        programDTO.setDescription(userProgramExercises.getUserProgram().getProgram().getName());
        programDTO.setStartDate(userProgramExercises.getUserProgram().getProgram().getStartDate());
        programDTO.setEndDate(userProgramExercises.getUserProgram().getProgram().getEndDate());

        List<ProgramExercise> programExercises = programExerciseRepo.findProgramExerciseByProgramId(userProgramExercises.getUserProgram().getProgram().getId());
        List<ProgramExerciseDTO> exercises = new ArrayList<>();
        for(ProgramExercise pe : programExercises){
            ProgramExerciseDTO programExerciseDTO = new ProgramExerciseDTO();
            programExerciseDTO.setProgramId(pe.getProgram().getId());
            programExerciseDTO.setExerciseId(pe.getExercise().getId());
            programExerciseDTO.setSets(pe.getSets());
            programExerciseDTO.setReps(pe.getReps());
            programExerciseDTO.setDayNumber(pe.getDayNumber());
            programExerciseDTO.setOrderIndex(pe.getOrderIndex());

            exercises.add(programExerciseDTO);
        }

        userProgramDTO.setProgram(programDTO);
        userProgramDTO.setExercises(exercises);

        return userProgramDTO;
    }
}
