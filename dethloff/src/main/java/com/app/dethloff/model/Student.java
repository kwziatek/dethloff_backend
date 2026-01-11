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
public class Student extends Person{

    @ManyToMany(mappedBy = "students")
    @Builder.Default
    private List<Course> courses = new ArrayList<>();
}
