package com.app.dethloff.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "set_of_courses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetOfCoursesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "setOfCourses")
    private List<CourseEntity> courses;
}
