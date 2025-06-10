package com.example.fitness_app_backend.mapper;

import com.example.fitness_app_backend.dto.programs.*;
import com.example.fitness_app_backend.model.Program;
import com.example.fitness_app_backend.model.ProgramExercise;
import com.example.fitness_app_backend.model.UserExerciseLog;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProgramMapper {
    public UserProgramDTO toUserProgramDTO(Program program, List<ProgramExercise> programExercises, List<UserExerciseLog> logs){
        Map<Long, List<UserExerciseLog>> logMap = logs.stream()
                .collect(Collectors.groupingBy(log -> log.getProgramExercise().getId()));

        Map<Integer, List<ProgramExerciseDTO>> daysMap = new TreeMap<>();

        for (ProgramExercise pe : programExercises) {
            ProgramExerciseDTO dto = new ProgramExerciseDTO();
            dto.setProgramExerciseId(pe.getId());
            dto.setExerciseId(pe.getExercise().getId());
            dto.setExerciseName(pe.getExercise().getName());
            dto.setSets(pe.getSets());
            dto.setReps(pe.getReps());
            dto.setOrderIndex(pe.getOrderIndex());
            dto.setDayNumber(pe.getDayNumber());

            List<UserExerciseLog> exerciseLogs = logMap.get(pe.getId());
            if (exerciseLogs != null && !exerciseLogs.isEmpty()) {
                List<ExerciseLogDTO> logDTOs = exerciseLogs.stream()
                        .sorted(Comparator.comparing(UserExerciseLog::getSetNumber))
                        .map(log -> {
                            ExerciseLogDTO logDTO = new ExerciseLogDTO();
                            logDTO.setSetNumber(log.getSetNumber());
                            logDTO.setCompletedReps(log.getCompletedReps());
                            logDTO.setWeightUsed(log.getWeightUsed());
                            logDTO.setWorkoutDate(log.getWorkoutDate());
                            return logDTO;
                        })
                        .collect(Collectors.toList());

                dto.setLogs(logDTOs);
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

        int maxDay = daysMap.keySet().stream().max(Integer::compareTo).orElse(1);

        int currentDay = logs.isEmpty()
                ? 1
                : (logs.stream()
                .map(log -> log.getProgramExercise().getDayNumber())
                .max(Integer::compareTo)
                .orElse(0) % maxDay) + 1;

        currentDay = Math.min(currentDay, maxDay);

        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setId(program.getId());
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
