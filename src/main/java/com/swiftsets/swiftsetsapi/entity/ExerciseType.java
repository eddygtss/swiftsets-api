package com.swiftsets.swiftsetsapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"category", "equipment"})
})
public class ExerciseType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String category;
    private String equipment;

    @JsonManagedReference
    @OneToMany(mappedBy = "exerciseType")
    private List<Exercise> exercises;
}
