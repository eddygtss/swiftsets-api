package com.swiftsets.swiftsetsapi.service;

import com.swiftsets.swiftsetsapi.entity.Exercise;
import com.swiftsets.swiftsetsapi.entity.ExerciseType;
import com.swiftsets.swiftsetsapi.model.ExerciseCategoryResponse;
import com.swiftsets.swiftsetsapi.model.ExerciseEquipmentResponse;
import com.swiftsets.swiftsetsapi.model.ExerciseTypesResponse;
import com.swiftsets.swiftsetsapi.repository.ExerciseRepository;
import com.swiftsets.swiftsetsapi.repository.ExerciseTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExerciseService {
    ExerciseTypeRepository exerciseTypeRepository;
    ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseTypeRepository exerciseTypeRepository, ExerciseRepository exerciseRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public ResponseEntity<String> addExerciseType(ExerciseType exerciseType) {
        try {
            ExerciseType newExerciseType = new ExerciseType();
            newExerciseType.setEquipment(exerciseType.getEquipment());
            newExerciseType.setCategory(exerciseType.getCategory());

            exerciseTypeRepository.save(newExerciseType);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(String.format(
                    "Unable to add %s and %s, exercise type already exists.", exerciseType.getCategory(), exerciseType.getEquipment()),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(String.format("Successfully added %s %s", exerciseType.getCategory(), exerciseType.getEquipment()), HttpStatus.OK);
    }

    public ResponseEntity<String> addExercise(Exercise exercise) {
        try {
            Exercise newExercise = new Exercise();
            newExercise.setName(exercise.getName());
            newExercise.setWeightedRating(exercise.getWeightedRating());

            ExerciseType exerciseType =
                    exerciseTypeRepository.findExerciseTypeByCategoryAndEquipment(exercise.getExerciseType().getCategory(), exercise.getExerciseType().getEquipment());

            if (exerciseType == null) {
                exerciseType = new ExerciseType();
                exerciseType.setCategory(exercise.getExerciseType().getCategory());
                exerciseType.setEquipment(exercise.getExerciseType().getEquipment());
                exerciseTypeRepository.save(exerciseType);
            }

            newExercise.setExerciseType(exerciseType);

            exerciseRepository.save(newExercise);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format(
                    "Unable to add %s, exercise already exists.", exercise.getName()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(String.format("Successfully added %s", exercise.getName()), HttpStatus.OK);
    }

    public ExerciseTypesResponse getAllExerciseTypes() {
        return ExerciseTypesResponse.builder().exerciseTypes((List<ExerciseType>) exerciseTypeRepository.findAll()).build();
    }

    public ExerciseCategoryResponse getExercisesByCategory(String category) {
        ExerciseCategoryResponse response = ExerciseCategoryResponse.builder().build();

        ExerciseType exerciseType = exerciseTypeRepository.findExerciseTypeByCategory(category);

        if (exerciseType != null) {
            response.setCategory(exerciseType.getCategory());
            response.setExercises(exerciseType.getExercises());
        }

        return response;
    }

    public ExerciseEquipmentResponse getExercisesByEquipment(String equipment) {
        ExerciseEquipmentResponse response = ExerciseEquipmentResponse.builder().build();

        ExerciseType exerciseType = exerciseTypeRepository.findExerciseTypeByEquipment(equipment);

        if (exerciseType != null) {
            response.setEquipment(exerciseType.getEquipment());
            response.setExercises(exerciseType.getExercises());
        }

        return response;
    }
}
