package com.app.dethloff.rest.controller;

import com.app.dethloff.rest.DTO.StudentRequestDTO;
import com.app.dethloff.rest.DTO.StudentResponseDTO;
import com.app.dethloff.rest.DTO.validation_groups.CreateAction;
import com.app.dethloff.rest.DTO.validation_groups.UpdateAction;
import com.app.dethloff.service.inte.StudentService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class StudentController {

  private final StudentService studentService;

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
  public ResponseEntity<StudentResponseDTO> createStudent(
      @Validated(CreateAction.class) @RequestBody StudentRequestDTO studentDTO) {
    StudentResponseDTO createdStudent = studentService.create(studentDTO);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdStudent.id())
            .toUri();

    return ResponseEntity.created(location).body(createdStudent);
  }

  @PutMapping("/students")
  public ResponseEntity<StudentResponseDTO> updateStudent(
      @Validated(UpdateAction.class) @RequestBody StudentRequestDTO studentDTO) {
    StudentResponseDTO updatedStudent = studentService.update(studentDTO);

    return ResponseEntity.ok(updatedStudent);
  }

  @DeleteMapping("/students/{studentId}")
  public ResponseEntity<?> deleteStudent(@PathVariable String studentId) {
    studentService.delete(studentId);

    return ResponseEntity.noContent().build();
  }
}
