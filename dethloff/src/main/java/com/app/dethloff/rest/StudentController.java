package com.app.dethloff.rest;


import com.app.dethloff.model.DTO.BasicStudentDTO;
import com.app.dethloff.model.DTO.DetailedStudentDTO;
import com.app.dethloff.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<List<BasicStudentDTO>> getStudents() {
        List<BasicStudentDTO> students = studentService.getAll();

        return ResponseEntity.ok(students);
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<DetailedStudentDTO> getStudent(@PathVariable String studentId) {
        DetailedStudentDTO studentDTO = studentService.get(studentId);

        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping("/students")
    public ResponseEntity<DetailedStudentDTO> createStudent(@RequestBody DetailedStudentDTO studentDTO) {
        DetailedStudentDTO createdStudent = studentService.create(studentDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStudent.id())
                .toUri();

        return ResponseEntity.created(location).body(createdStudent);
    }

    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable String studentId) {
        studentService.delete(studentId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/students")
    public ResponseEntity<DetailedStudentDTO> updateStudent(@RequestBody DetailedStudentDTO studentDTO) {
        DetailedStudentDTO updatedStudent = studentService.update(studentDTO);

        return ResponseEntity.ok(updatedStudent);
    }

}


