package com.app.dethloff.rest;


import com.app.dethloff.DTO.StudentDTO;
import com.app.dethloff.error.StudentNotFoundException;
import com.app.dethloff.model.Student;
import com.app.dethloff.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        List<Student> theStudents = new ArrayList<>();

        theStudents.add(Student.builder()
                .id("1")
                .name("maja")
                .surname("nowasczyk")
                .build());

        theStudents.add(Student.builder()
                .id("2")
                .name("karol")
                .surname("grzyma")
                .build());

        return theStudents;
    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {



        List<Student> theStudents = new ArrayList<>();

        theStudents.add(Student.builder()
                .id("1")
                .name("maja")
                .surname("nowasczyk")
                .build());

        theStudents.add(Student.builder()
                .id("2")
                .name("karol")
                .surname("grzyma")
                .build());

        if(studentId >= theStudents.size() || studentId < 0) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }

        return theStudents.get(studentId);
    }

    @PostMapping("/students")
    public void createStudent(@RequestBody StudentDTO studentDTO) {
        studentService.create(studentDTO);
    }

}


