package com.swiftsets.swiftsetsapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"category", "equipment"})
})
@AllArgsConstructor
@NoArgsConstructor
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
