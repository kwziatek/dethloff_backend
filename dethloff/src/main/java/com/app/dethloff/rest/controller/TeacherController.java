package com.app.dethloff.rest.controller;

import com.app.dethloff.rest.DTO.TeacherRequestDTO;
import com.app.dethloff.rest.DTO.TeacherResponseDTO;
import com.app.dethloff.rest.DTO.validation_groups.CreateAction;
import com.app.dethloff.rest.DTO.validation_groups.UpdateAction;
import com.app.dethloff.service.TeacherService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class TeacherController {

  private final TeacherService teacherService;

  @Autowired
  public TeacherController(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @GetMapping("/teachers/{teacherId}")
  public ResponseEntity<TeacherResponseDTO> getTeacher(@PathVariable String teacherId) {
    TeacherResponseDTO teacherDTO = teacherService.get(teacherId);

    return ResponseEntity.ok(teacherDTO);
  }

  @GetMapping("/teachers")
  public ResponseEntity<List<TeacherResponseDTO>> getAllTeacher() {
    List<TeacherResponseDTO> teacherDTOs = teacherService.getAll();

    return ResponseEntity.ok(teacherDTOs);
  }

  @PostMapping("/teachers")
  public ResponseEntity<TeacherResponseDTO> createTeacher(
      @Validated(CreateAction.class) @RequestBody TeacherRequestDTO teacherDTO) {
    TeacherResponseDTO createdTeacher = teacherService.create(teacherDTO);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/id")
            .buildAndExpand(createdTeacher.id())
            .toUri();

    return ResponseEntity.created(location).body(createdTeacher);
  }

  @PutMapping("/teachers")
  public ResponseEntity<TeacherResponseDTO> updateTeacher(
      @Validated(UpdateAction.class) @RequestBody TeacherRequestDTO teacherDTO) {
    TeacherResponseDTO updatedTeacher = teacherService.update(teacherDTO);

    return ResponseEntity.ok(updatedTeacher);
  }

  @DeleteMapping("/teachers/{teacherId}")
  public ResponseEntity<?> deleteTeacher(@PathVariable String teacherId) {
    teacherService.delete(teacherId);

    return ResponseEntity.noContent().build();
  }
}
