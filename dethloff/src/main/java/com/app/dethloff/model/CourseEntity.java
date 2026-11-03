package com.app.dethloff.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
      inverseJoinColumns = @JoinColumn(name = "student_id"))
  List<StudentEntity> students = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private TeacherEntity teacher;

  @ManyToOne
  @JoinColumn(name = "set_of_courses_id")
  private SetOfCoursesEntity setOfCourses;

  @Column(name = "room")
  String room;

  @Column(name = "total_course_time")
  Integer totalCourseTime;

  @Column(name = "completed_course_time")
  Integer completedCourseTime;

  // Course also needs start date and end date - it's vital to determine if a course is current or
  // not. Might be determined according to oldest and newest lesson. (Student and Teacher are active
  // iff they are assigned to current course).
  // TODO: implement what's above

  public void addStudent(StudentEntity student) {
    if (students == null) {
      students = new ArrayList<>();
    }

    if (!students.contains(student)) {
      students.add(student);
      student.getCourses().add(this);
    }
  }

  public void removeStudent(StudentEntity student) {
    if (students != null && students.contains(student)) {
      students.remove(student);
      student.getCourses().remove(this);
    }
  }
}
