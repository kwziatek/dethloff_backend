package com.app.dethloff.rest;


import com.app.dethloff.model.DTO.StudentRequestDTO;
import com.app.dethloff.model.DTO.StudentResponseDTO;
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
    public ResponseEntity<List<StudentResponseDTO>> getStudents() {
        List<StudentResponseDTO> students = studentService.getAll();

        return ResponseEntity.ok(students);
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable String studentId) {
        StudentResponseDTO studentDTO = studentService.get(studentId);

        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping("/students")
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody StudentRequestDTO studentDTO) {
        StudentResponseDTO createdStudent = studentService.create(studentDTO);

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
    public ResponseEntity<StudentResponseDTO> updateStudent(@RequestBody StudentRequestDTO studentDTO) {
        StudentResponseDTO updatedStudent = studentService.update(studentDTO);

        return ResponseEntity.ok(updatedStudent);
    }

}


