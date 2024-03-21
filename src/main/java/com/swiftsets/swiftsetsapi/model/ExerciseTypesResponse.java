package com.swiftsets.swiftsetsapi.model;

import com.swiftsets.swiftsetsapi.entity.ExerciseType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExerciseTypesResponse {
    List<ExerciseType> exerciseTypes;
}
