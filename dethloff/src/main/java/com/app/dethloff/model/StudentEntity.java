package com.app.dethloff.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "student")
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StudentEntity extends PersonAbstractEntity {

    @ManyToMany(mappedBy = "students")
    @Builder.Default
    private List<CourseEntity> courses = new ArrayList<>();

    @Column(nullable = false)
    private Boolean isActive = false;
}
