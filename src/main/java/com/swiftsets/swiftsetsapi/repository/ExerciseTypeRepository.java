package com.swiftsets.swiftsetsapi.repository;

import com.swiftsets.swiftsetsapi.entity.ExerciseType;
import org.springframework.data.repository.CrudRepository;

public interface ExerciseTypeRepository extends CrudRepository<ExerciseType, Integer> {
    ExerciseType findExerciseTypeByCategoryAndEquipment(String category, String equipment);
    ExerciseType findExerciseTypeByCategory(String category);
    ExerciseType findExerciseTypeByEquipment(String equipment);
}
