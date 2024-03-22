package com.swiftsets.swiftsetsapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String name;
    private int weightedRating;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="exercise_type_id", nullable=false)
    private ExerciseType exerciseType;
}
