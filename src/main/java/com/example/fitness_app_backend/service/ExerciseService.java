package com.example.fitness_app_backend.service;


import com.example.fitness_app_backend.dto.exercises.CreateExerciseDTO;
import com.example.fitness_app_backend.dto.exercises.ExerciseDTO;
import com.example.fitness_app_backend.model.Exercise;
import com.example.fitness_app_backend.model.User;
import com.example.fitness_app_backend.repository.ExerciseRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExerciseService {
    private final static Logger logger = LoggerFactory.getLogger(ExerciseService.class);
    private final ExerciseRepo exerciseRepo;
    private final UserService userService;

    public List<ExerciseDTO> getAllExercises(){
        logger.info("Getting all exercises");
        List<Exercise> exercises = exerciseRepo.findGlobalOrUserExercises(userService.getCurrentUserId());
        return exercises.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> createExercise(CreateExerciseDTO dto){
        User user = userService.getCurrentUser();
        logger.info("User {} creating an exercise.", user.getId());

        Exercise exercise = new Exercise();
        exercise.setName(dto.getName());
        exercise.setDescription(dto.getDescription());
        exercise.setCreatedAt(LocalDateTime.now());
        exercise.setUpdatedAt(LocalDateTime.now());
        exercise.setUser(user);

        exerciseRepo.save(exercise);

        return ResponseEntity.ok(Map.of("message", "Exercise created successfully."));


    }

    private ExerciseDTO mapToDTO(Exercise exercise){
        ExerciseDTO dto = new ExerciseDTO();
        dto.setId(exercise.getId());
        dto.setName(exercise.getName());
        dto.setDescription(exercise.getDescription());

        return dto;
    }



}
