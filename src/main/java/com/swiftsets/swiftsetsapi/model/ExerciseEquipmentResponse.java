package com.swiftsets.swiftsetsapi.model;

import com.swiftsets.swiftsetsapi.entity.Exercise;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExerciseEquipmentResponse {
    String equipment;
    List<Exercise> exercises;
}
