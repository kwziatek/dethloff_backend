package com.app.dethloff.rest;


import com.app.dethloff.DTO.StudentDTO;
import com.app.dethloff.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<StudentDTO> getStudents() {
        return studentService.getAll();
    }

    @GetMapping("/students/{studentId}")
    public StudentDTO getStudent(@PathVariable String studentId) {
        return studentService.get(studentId);
    }

    @PostMapping("/students")
    public void createStudent(@RequestBody StudentDTO studentDTO) {
        studentService.create(studentDTO);
    }

    @DeleteMapping("/students/{studentId}")
    public void deleteStudent(@PathVariable String studentId) {
        studentService.delete(studentId);
    }

    @PutMapping("/students")
    public void updateStudent(@RequestBody StudentDTO studentDTO) {
        studentService.update(studentDTO);
    }

}


