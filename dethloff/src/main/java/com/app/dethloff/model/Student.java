package com.app.dethloff.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "surname")
    String surname;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;
}
