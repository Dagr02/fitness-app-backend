package com.example.fitness_app_backend.controller;

import com.example.fitness_app_backend.dto.programs.create.CreateProgramDTO;
import com.example.fitness_app_backend.dto.programs.UserProgramDTO;
import com.example.fitness_app_backend.dto.programs.update.UpdateProgramDTO;
import com.example.fitness_app_backend.service.ProgramService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/programs")
public class ProgramController {
    private final ProgramService programService;

    @PostMapping("/custom")
    public ResponseEntity<?> createCustomProgram(@RequestBody CreateProgramDTO dto){
        UserProgramDTO userProgramDTO = programService.createCustomProgram(dto);
        return ResponseEntity.ok(userProgramDTO);
    }

    @PutMapping("/custom/update")
    public ResponseEntity<?> updateCustomProgram(@RequestBody UpdateProgramDTO dto){
        UserProgramDTO userProgramDTO = programService.updateCustomProgram(dto);
        return ResponseEntity.ok(userProgramDTO);
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable("programId") Long programId){
        programService.deleteProgram(programId);
        return ResponseEntity.noContent().build();
    }
}
