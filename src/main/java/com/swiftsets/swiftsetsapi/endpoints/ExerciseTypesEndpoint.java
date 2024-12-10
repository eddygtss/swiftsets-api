package com.swiftsets.swiftsetsapi.endpoints;

import com.swiftsets.swiftsetsapi.repository.ExerciseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import swiftsetsapi.swiftsets.com.GetAllExerciseTypesResponse;

import java.util.List;

@Endpoint
@Component
public class ExerciseTypesEndpoint {
    private static final String NAMESPACE_URI = "com.swiftsets.swiftsetsapi";

    private ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    public ExerciseTypesEndpoint(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getExerciseTypesRequest")
    @ResponsePayload
    public GetAllExerciseTypesResponse getAllExerciseTypesResponse() {
        GetAllExerciseTypesResponse response = new GetAllExerciseTypesResponse();
        response.getExerciseTypes().addAll((List) exerciseTypeRepository.findAll());
        return response;
    }
}
