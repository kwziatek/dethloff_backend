package com.app.dethloff.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "course")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @Column(name = "name")
    String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_level")
    CourseLevel level;

    @Column(name = "description")
    String description;

    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    List<StudentEntity> students = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "teacherId")
    private TeacherEntity teacher;

    public void addStudent(StudentEntity student) {
        if(students == null) {
            students = new ArrayList<>();
        }

        if(!students.contains(student)) {
            students.add(student);
            student.getCourses().add(this);
        }

    }

    public void removeStudent(StudentEntity student) {
        if(students != null && students.contains(student)) {
            students.remove(student);
            student.getCourses().remove(this);
        }

    }

}
