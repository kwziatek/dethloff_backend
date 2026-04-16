package com.app.dethloff.rest;

import com.app.dethloff.model.DTO.BasicTeacherDTO;
import com.app.dethloff.model.DTO.DetailedTeacherDTO;
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
    public ResponseEntity<DetailedTeacherDTO> getTeacher(@PathVariable String teacherId) {
        DetailedTeacherDTO teacherDTO = teacherService.get(teacherId);

        return ResponseEntity.ok(teacherDTO);
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<BasicTeacherDTO>> getAllTeacher() {
        List<BasicTeacherDTO> teacherDTOs = teacherService.getAll();

        return ResponseEntity.ok(teacherDTOs);
    }

    @PostMapping("/teachers")
    public ResponseEntity<DetailedTeacherDTO> createTeacher(@RequestBody DetailedTeacherDTO teacherDTO) {
        DetailedTeacherDTO createdTeacher = teacherService.create(teacherDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id")
                .buildAndExpand(createdTeacher.id())
                .toUri();

        return ResponseEntity.created(location).body(createdTeacher);
    }

    @PutMapping("/teachers")
    public ResponseEntity<DetailedTeacherDTO> updateTeacher(@RequestBody DetailedTeacherDTO teacherDTO) {
        DetailedTeacherDTO updatedTeacher = teacherService.update(teacherDTO);

        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/teachers/{teacherId}")
    public ResponseEntity<?> deleteTeacher(@PathVariable String teacherId) {
        teacherService.delete(teacherId);

        return ResponseEntity.noContent().build();
    }
}
