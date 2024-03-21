package com.swiftsets.swiftsetsapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String name;
    private int weightRating;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="exercise_type_id", nullable=false)
    private ExerciseType exerciseType;
}
