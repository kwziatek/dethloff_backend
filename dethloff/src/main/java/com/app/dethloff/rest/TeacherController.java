package com.app.dethloff.rest;

import com.app.dethloff.model.DTO.TeacherRequestDTO;
import com.app.dethloff.model.DTO.TeacherResponseDTO;
import com.app.dethloff.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TeacherController {

    TeacherService teacherService;

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
    public ResponseEntity<TeacherResponseDTO> createTeacher(@RequestBody TeacherRequestDTO teacherDTO) {
        TeacherResponseDTO createdTeacher = teacherService.create(teacherDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id")
                .buildAndExpand(createdTeacher.id())
                .toUri();

        return ResponseEntity.created(location).body(createdTeacher);
    }

    @PutMapping("/teachers")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@RequestBody TeacherRequestDTO teacherDTO) {
        TeacherResponseDTO updatedTeacher = teacherService.update(teacherDTO);

        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/teachers/{teacherId}")
    public ResponseEntity<TeacherResponseDTO> deleteTeacher(@PathVariable String teacherId) {
        teacherService.delete(teacherId);

        return ResponseEntity.noContent().build();
    }
}
