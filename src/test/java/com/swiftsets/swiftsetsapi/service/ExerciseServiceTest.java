package com.swiftsets.swiftsetsapi.service;

import com.swiftsets.swiftsetsapi.entity.Exercise;
import com.swiftsets.swiftsetsapi.entity.ExerciseType;
import com.swiftsets.swiftsetsapi.model.ExerciseCategoryResponse;
import com.swiftsets.swiftsetsapi.model.ExerciseEquipmentResponse;
import com.swiftsets.swiftsetsapi.model.ExerciseTypesResponse;
import com.swiftsets.swiftsetsapi.repository.ExerciseRepository;
import com.swiftsets.swiftsetsapi.repository.ExerciseTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ExerciseServiceTest {
    @Mock
    ExerciseRepository mockExerciseRepository;
    @Mock
    ExerciseTypeRepository mockExerciseTypeRepository;

    ExerciseService exerciseService;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
        exerciseService = new ExerciseService(
                mockExerciseTypeRepository,
                mockExerciseRepository
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getExerciseTypes_returnsExerciseTypes() {
        ExerciseTypesResponse expected = stubExerciseTypesResponse();

        when(mockExerciseTypeRepository.findAll())
                .thenReturn(stubExerciseTypeRepositoryResponse());

        ExerciseTypesResponse actual = exerciseService.getAllExerciseTypes();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void addExerciseType_returnsSuccessful_whenExerciseTypeDoesNotExist() {
        ExerciseType newExerciseType = new ExerciseType();
        newExerciseType.setCategory("Back");
        newExerciseType.setEquipment("Barbell");
        ResponseEntity<String> expected = getSuccessfulExerciseTypeResponse(newExerciseType);

        when(mockExerciseTypeRepository.save(newExerciseType))
                .thenReturn(newExerciseType);

        ResponseEntity<String> actual = exerciseService.addExerciseType(newExerciseType);

        verify(mockExerciseTypeRepository).save(newExerciseType);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void addExerciseType_returnsError_whenExerciseTypeAlreadyExists() {
        ExerciseType newExerciseType = new ExerciseType();
        newExerciseType.setCategory("Back");
        newExerciseType.setEquipment("Barbell");
        ResponseEntity<String> expected = getErrorExerciseTypeResponse(newExerciseType);

        when(mockExerciseTypeRepository.save(newExerciseType))
                .thenThrow(DataIntegrityViolationException.class);

        ResponseEntity<String> actual = exerciseService.addExerciseType(newExerciseType);

        verify(mockExerciseTypeRepository).save(newExerciseType);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void addExercise_returnsSuccessful_whenExerciseDoesNotExist() {
        Exercise newExercise = new Exercise();
        newExercise.setName("Squat");
        newExercise.setWeightedRating(100);

        ExerciseType newExerciseType = new ExerciseType();
        newExerciseType.setEquipment("Barbell");
        newExerciseType.setCategory("Legs");

        newExercise.setExerciseType(newExerciseType);

        ResponseEntity<String> expected = getSuccessfulExerciseResponse(newExercise);

        when(mockExerciseRepository.save(newExercise))
                .thenReturn(newExercise);

        when(mockExerciseTypeRepository.save(newExerciseType))
                .thenReturn(newExerciseType);

        ResponseEntity<String> actual = exerciseService.addExercise(newExercise);

        verify(mockExerciseRepository).save(newExercise);
        verify(mockExerciseTypeRepository).save(newExerciseType);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void addExercise_returnsSuccessful_whenExerciseDoesNotExistAndExerciseTypeExists() {
        Exercise newExercise = new Exercise();
        newExercise.setName("Squat");
        newExercise.setWeightedRating(100);

        ExerciseType newExerciseType = new ExerciseType();
        newExerciseType.setEquipment("Barbell");
        newExerciseType.setCategory("Legs");

        newExercise.setExerciseType(newExerciseType);

        ResponseEntity<String> expected = getSuccessfulExerciseResponse(newExercise);

        when(mockExerciseRepository.save(newExercise))
                .thenReturn(newExercise);

        when(mockExerciseTypeRepository.findExerciseTypeByCategoryAndEquipment(
                newExerciseType.getCategory(),
                newExerciseType.getEquipment())).thenReturn(newExerciseType);

        ResponseEntity<String> actual = exerciseService.addExercise(newExercise);

        verify(mockExerciseRepository).save(newExercise);
        verify(mockExerciseTypeRepository).findExerciseTypeByCategoryAndEquipment(anyString(), anyString());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void addExercise_returnsError_whenExerciseAlreadyExists() {
        Exercise newExercise = new Exercise();
        newExercise.setName("Squat");
        newExercise.setWeightedRating(100);
        ResponseEntity<String> expected = getErrorExerciseResponse(newExercise);

        when(mockExerciseRepository.save(newExercise))
                .thenThrow(DataIntegrityViolationException.class);

        ResponseEntity<String> actual = exerciseService.addExercise(newExercise);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getExercisesByCategory_returnsExercisesInTheSameCategory() {
        ExerciseCategoryResponse expected = stubMultipleExerciseCategoryResponse();

        when(mockExerciseTypeRepository.findExerciseTypeByCategory(anyString()))
                .thenReturn(getMultipleExerciseTypeWithMultipleExercises());

        ExerciseCategoryResponse actual = exerciseService.getExercisesByCategory("Chest");

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getExercisesByCategory_returnsEmptyExercises_whenNoExercisesFoundForCategory() {
        ExerciseCategoryResponse expected = stubEmptyExerciseCategoryResponse();

        when(mockExerciseTypeRepository.findExerciseTypeByCategory(anyString()))
                .thenReturn(null);

        ExerciseCategoryResponse actual = exerciseService.getExercisesByCategory("Chest");

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getExercisesByEquipment_returnsExercisesUsingTheSameEquipment() {
        ExerciseEquipmentResponse expected = stubMultipleExerciseEquipmentResponse();

        when(mockExerciseTypeRepository.findExerciseTypeByEquipment(anyString()))
                .thenReturn(getMultipleExerciseTypeWithMultipleExercises());

        ExerciseEquipmentResponse actual = exerciseService.getExercisesByEquipment("Barbell");

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getExercisesByEquipment_returnsEmptyExercises_whenNoExercisesFoundForEquipment() {
        ExerciseEquipmentResponse expected = stubEmptyExerciseEquipmentResponse();

        when(mockExerciseTypeRepository.findExerciseTypeByEquipment(anyString()))
                .thenReturn(null);

        ExerciseEquipmentResponse actual = exerciseService.getExercisesByEquipment("Barbell");

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private ExerciseEquipmentResponse stubEmptyExerciseEquipmentResponse() {
        return ExerciseEquipmentResponse.builder().build();
    }

    private ExerciseEquipmentResponse stubMultipleExerciseEquipmentResponse() {
        return ExerciseEquipmentResponse.builder()
                .equipment("Barbell")
                .exercises(List.of(
                        Exercise.builder()
                                .name("Bench Press")
                                .weightedRating(100)
                                .build(),
                        Exercise.builder()
                                .name("Incline Bench Press")
                                .weightedRating(95)
                                .build()
                ))
                .build();
    }

    private ExerciseCategoryResponse stubEmptyExerciseCategoryResponse() {
        return ExerciseCategoryResponse.builder().build();
    }

    private ExerciseCategoryResponse stubMultipleExerciseCategoryResponse() {
        return ExerciseCategoryResponse.builder()
                .category("Chest")
                .exercises(List.of(
                        Exercise.builder()
                                .name("Bench Press")
                                .weightedRating(100)
                                .build(),
                        Exercise.builder()
                                .name("Incline Bench Press")
                                .weightedRating(95)
                                .build()
                ))
                .build();
    }

    private ExerciseType getMultipleExerciseTypeWithMultipleExercises() {
        return ExerciseType.builder()
                .category("Chest")
                .equipment("Barbell")
                .exercises(List.of(
                        Exercise.builder()
                                .name("Bench Press")
                                .weightedRating(100)
                                .build(),
                        Exercise.builder()
                                .name("Incline Bench Press")
                                .weightedRating(95)
                                .build()
                ))
                .build();
    }

    private ResponseEntity<String> getSuccessfulExerciseTypeResponse(ExerciseType exerciseType) {
        return new ResponseEntity<>(String.format("Successfully added %s %s", exerciseType.getCategory(), exerciseType.getEquipment()), HttpStatus.OK);
    }

    private ResponseEntity<String> getErrorExerciseTypeResponse(ExerciseType exerciseType) {
        return new ResponseEntity<>(String.format(
                "Unable to add %s and %s, exercise type already exists.", exerciseType.getCategory(), exerciseType.getEquipment()),
                HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> getSuccessfulExerciseResponse(Exercise exercise) {
        return new ResponseEntity<>(String.format("Successfully added %s", exercise.getName()), HttpStatus.OK);
    }

    private ResponseEntity<String> getErrorExerciseResponse(Exercise exercise) {
        return new ResponseEntity<>(String.format(
                "Unable to add %s, exercise already exists.", exercise.getName()), HttpStatus.BAD_REQUEST);
    }

    private List<ExerciseType> stubExerciseTypeRepositoryResponse() {
        ExerciseType exerciseType = new ExerciseType();
        exerciseType.setEquipment("Barbell");
        exerciseType.setCategory("Chest");

        return Collections.singletonList(exerciseType);
    }

    private ExerciseTypesResponse stubExerciseTypesResponse() {
        ExerciseType exerciseType = new ExerciseType();
        exerciseType.setEquipment("Barbell");
        exerciseType.setCategory("Chest");

        return ExerciseTypesResponse.builder()
                .exerciseTypes(List.of(exerciseType))
                .build();
    }
}
