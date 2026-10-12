package com.app.dethloff.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "lesson")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;

    @Column(name = "completed")
    private boolean completed;

    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL)
    @ToString.Exclude
    private LessonTimeEntity lessonTime;
}
