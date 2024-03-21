package com.swiftsets.swiftsetsapi.service;

import com.swiftsets.swiftsetsapi.entity.Exercise;
import com.swiftsets.swiftsetsapi.entity.ExerciseType;
import com.swiftsets.swiftsetsapi.model.ExerciseTypesResponse;
import com.swiftsets.swiftsetsapi.repository.ExerciseRepository;
import com.swiftsets.swiftsetsapi.repository.ExerciseTypeRepository;
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

    public String addExerciseType(ExerciseType exerciseType) {
        try {
            ExerciseType newExerciseType = new ExerciseType();
            newExerciseType.setEquipment(exerciseType.getEquipment());
            newExerciseType.setCategory(exerciseType.getCategory());

            exerciseTypeRepository.save(newExerciseType);
        } catch (Exception e) {
            System.out.println(e);
        }

        return String.format("Successfully added %s %s", exerciseType.getCategory(), exerciseType.getEquipment());
    }

    public String addExercise(Exercise exercise) {
        try {
            Exercise newExercise = new Exercise();
            newExercise.setName(exercise.getName());
            newExercise.setWeightRating(exercise.getWeightRating());

            ExerciseType exerciseType = new ExerciseType();

            try {
                exerciseType =
                        exerciseTypeRepository.findExerciseTypeByCategoryAndEquipment(exercise.getExerciseType().getCategory(), exercise.getExerciseType().getEquipment());
            } catch (Exception e) {
                exerciseType.setCategory(exercise.getExerciseType().getCategory());
                exerciseType.setEquipment(exercise.getExerciseType().getEquipment());
                exerciseTypeRepository.save(exerciseType);
            }

            newExercise.setExerciseType(exerciseType);

            exerciseRepository.save(newExercise);
        } catch (Exception e) {
            System.out.println(e);
        }

        return String.format("Successfully added %s", exercise.getName());
    }
    public Iterable<ExerciseType> getExercises() {
        return exerciseTypeRepository.findAll();
    }
}
