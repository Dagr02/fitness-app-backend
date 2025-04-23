package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.dto.ExerciseStatsDTO;
import com.example.fitness_app_backend.model.Exercise;
import com.example.fitness_app_backend.model.ExerciseStats;
import com.example.fitness_app_backend.model.User;
import com.example.fitness_app_backend.model.WorkoutSession;
import com.example.fitness_app_backend.repository.ExerciseRepo;
import com.example.fitness_app_backend.repository.ExerciseStatsRepo;
import com.example.fitness_app_backend.repository.WorkoutSessionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkoutService {
    private final WorkoutSessionRepo workoutSessionRepo;
    private final ExerciseStatsRepo exerciseStatsRepo;
    private final ExerciseRepo exerciseRepo;
    private final UserService userService;

    public WorkoutSession logWorkout(List<ExerciseStatsDTO> statsDTOS){
        User user = userService.getCurrentUser();
        LocalDateTime time = LocalDateTime.now();
        WorkoutSession session = new WorkoutSession();
        session.setUser(user);
        session.setSessionDate(time);
        session = workoutSessionRepo.save(session);

        for(ExerciseStatsDTO dto : statsDTOS){
            Exercise exercise = exerciseRepo.findById(dto.getExerciseId()).orElseThrow();

            ExerciseStats stat = new ExerciseStats();
            stat.setUser(user);
            stat.setSession(session);
            stat.setExercise(exercise);
            stat.setSets(dto.getSets());
            stat.setReps(dto.getReps());
            stat.setWeight(dto.getWeight());
            stat.setDate(time);
            exerciseStatsRepo.save(stat);

        }

        return session;
    }

}
