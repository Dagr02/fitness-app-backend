package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.dto.programs.UserExerciseLogDTO;
import com.example.fitness_app_backend.model.User;
import com.example.fitness_app_backend.model.UserExerciseLog;
import com.example.fitness_app_backend.repository.UserExerciseLogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserExerciseLogService {
    private final UserExerciseLogRepo userExerciseLogRepo;
    private final UserService userService;

    public List<UserExerciseLogDTO> getUserExerciseLogs(){
        User user = userService.getCurrentUser();
        List<UserExerciseLog> logs = userExerciseLogRepo.findByUserOrderByWorkoutDateAsc(user);

        return logs.stream().map(UserExerciseLogDTO::new).collect(Collectors.toList());

    }

    public List<UserExerciseLogDTO> getUserExerciseLogsByExercise(Long exerciseId) {
        User user = userService.getCurrentUser();
        List<UserExerciseLog> logs = userExerciseLogRepo.findByUserAndProgramExercise_Exercise_IdOrderByWorkoutDateAsc(user, exerciseId);
        return logs.stream().map(UserExerciseLogDTO::new).collect(Collectors.toList());
    }
}
