package com.swiftsets.swiftsetsapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftsets.swiftsetsapi.entity.Exercise;
import com.swiftsets.swiftsetsapi.entity.ExerciseType;
import com.swiftsets.swiftsetsapi.model.ExerciseCategoryResponse;
import com.swiftsets.swiftsetsapi.model.ExerciseEquipmentResponse;
import com.swiftsets.swiftsetsapi.model.ExerciseTypesResponse;
import com.swiftsets.swiftsetsapi.service.ExerciseService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExerciseController.class)
public class ExerciseControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    ExerciseService exerciseService;

    @Test
    @Disabled
    public void getAllExercises() throws Exception {
        when(exerciseService.getAllExerciseTypes())
                .thenReturn(ExerciseTypesResponse.builder().exerciseTypes(emptyList()).build());

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/get-all-types")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exerciseTypes").exists());
    }

    @Test
    @Disabled
    public void getExercisesByCategory() throws Exception {
        when(exerciseService.getExercisesByCategory(anyString()))
                .thenReturn(ExerciseCategoryResponse.builder().category("Back").build());

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/get-category")
                        .queryParam("category", "back")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").exists());
    }

    @Test
    @Disabled
    public void getExercisesByEquipment() throws Exception {
        when(exerciseService.getExercisesByEquipment(anyString()))
                .thenReturn(ExerciseEquipmentResponse.builder().equipment("Barbell").build());

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/get-equipment")
                        .queryParam("equipment", "barbell")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equipment").exists());
    }

    @Test
    @Disabled
    public void addExercise() throws Exception {
        when(exerciseService.addExercise(any()))
                .thenReturn(new ResponseEntity<>("Added new exercise", HttpStatus.OK));

        Exercise exercise = Exercise.builder().build();
        String requestBody = new ObjectMapper().writeValueAsString(exercise);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/add-exercise")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Added new exercise"));
    }

    @Test
    @Disabled
    public void addExerciseType() throws Exception {
        when(exerciseService.addExerciseType(any()))
                .thenReturn(new ResponseEntity<>("Added new exercise type", HttpStatus.OK));

        ExerciseType exerciseType = ExerciseType.builder().build();
        String requestBody = new ObjectMapper().writeValueAsString(exerciseType);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/add-exercise-type")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Added new exercise type"));
    }

}
