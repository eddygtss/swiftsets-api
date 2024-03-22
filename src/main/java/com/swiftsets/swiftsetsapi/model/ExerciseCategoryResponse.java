package com.swiftsets.swiftsetsapi.model;

import com.swiftsets.swiftsetsapi.entity.Exercise;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExerciseCategoryResponse {
    String category;
    List<Exercise> exercises;
}
