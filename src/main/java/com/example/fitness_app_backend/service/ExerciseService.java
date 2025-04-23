package com.example.fitness_app_backend.service;


import com.example.fitness_app_backend.dto.ExerciseDTO;
import com.example.fitness_app_backend.model.Exercise;
import com.example.fitness_app_backend.model.User;
import com.example.fitness_app_backend.repository.ExerciseRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseService {
    private final ExerciseRepo exerciseRepo;
    private final UserService userService;

    public Exercise createUserExercise(ExerciseDTO dto){
        Exercise ex = new Exercise();
        ex.setName(dto.getName());
        ex.setDescription(dto.getDescription());
        ex.setUser(userService.getCurrentUser());

        return exerciseRepo.save(ex);
    }

    public List<Exercise> getAvailableExercises(){
        User user = userService.getCurrentUser();
        return exerciseRepo.findAllByUserIsNullOrUser(user);
    }

}
