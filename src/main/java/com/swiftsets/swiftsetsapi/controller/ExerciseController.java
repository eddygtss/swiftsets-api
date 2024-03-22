package com.swiftsets.swiftsetsapi.controller;

import com.swiftsets.swiftsetsapi.entity.Exercise;
import com.swiftsets.swiftsetsapi.entity.ExerciseType;
import com.swiftsets.swiftsetsapi.model.ExerciseCategoryResponse;
import com.swiftsets.swiftsetsapi.model.ExerciseEquipmentResponse;
import com.swiftsets.swiftsetsapi.model.ExerciseTypesResponse;
import com.swiftsets.swiftsetsapi.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("/add-exercise-type")
    public ResponseEntity<String> addExerciseType(@RequestBody ExerciseType exerciseType) {
        return exerciseService.addExerciseType(exerciseType);
    }

    @PostMapping("/add-exercise")
    public ResponseEntity<String> addExercise(@RequestBody Exercise exercise) {
        return exerciseService.addExercise(exercise);
    }

    @GetMapping("/get-all-types")
    public ExerciseTypesResponse getAllExerciseTypes() {
        return exerciseService.getAllExerciseTypes();
    }

    @GetMapping("/get-category")
    public ExerciseCategoryResponse getExercisesByCategory(@RequestParam String category) {
        return exerciseService.getExercisesByCategory(category);
    }

    @GetMapping("/get-equipment")
    public ExerciseEquipmentResponse getExercisesByEquipment(@RequestParam String equipment) {
        return exerciseService.getExercisesByEquipment(equipment);
    }

}
