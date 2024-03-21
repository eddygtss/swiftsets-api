package com.swiftsets.swiftsetsapi.controller;

import com.swiftsets.swiftsetsapi.entity.Exercise;
import com.swiftsets.swiftsetsapi.entity.ExerciseType;
import com.swiftsets.swiftsetsapi.model.ExerciseTypesResponse;
import com.swiftsets.swiftsetsapi.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("/add-exercise-type")
    public ResponseEntity<String> addExerciseType(@RequestBody ExerciseType exerciseType) {
        String status = exerciseService.addExerciseType(exerciseType);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/add-exercise")
    public ResponseEntity<String> addExercise(@RequestBody Exercise exercise) {
        String status = exerciseService.addExercise(exercise);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public Iterable<ExerciseType> getAllExercise() {
        return exerciseService.getExercises();
    }


}
