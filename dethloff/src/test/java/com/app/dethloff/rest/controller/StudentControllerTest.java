package com.app.dethloff.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.dethloff.model.StudentEntity;
import com.app.dethloff.rest.DTO.StudentRequestDTO;
import com.app.dethloff.rest.DTO.StudentResponseDTO;
import com.app.dethloff.service.inte.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.mvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

  @Autowired private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @MockitoBean
  private StudentService studentService;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  // 1. getStudents
  @Test
  void getStudents_ShouldReturnList() throws Exception {
    StudentResponseDTO student = StudentResponseDTO.builder().id("1").name("John Doe").build();
    when(studentService.getAll()).thenReturn(List.of(student));

    mockMvc.perform(get("/api/students"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value("1"));
  }

  @Test
  void getStudents_ShouldReturnInternalServerError_WhenServiceFails() throws Exception {
    when(studentService.getAll()).thenThrow(new RuntimeException("Database error"));

    mockMvc.perform(get("/api/students"))
        .andExpect(status().isInternalServerError());
  }

  // 2. getStudent
  @Test
  void getStudent_ShouldReturnStudent() throws Exception {
    String id = "1";
    StudentResponseDTO student = StudentResponseDTO.builder().id(id).name("John Doe").build();
    when(studentService.get(id)).thenReturn(student);

    mockMvc.perform(get("/api/students/{studentId}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id));
  }

  @Test
  void getStudent_ShouldReturnNotFound_WhenStudentDoesNotExist() throws Exception {
    String id = "non-existent";
    when(studentService.get(id)).thenThrow(new com.app.dethloff.exceptions.model.StudentNotFoundException());

    mockMvc.perform(get("/api/students/{studentId}", id))
        .andExpect(status().isNotFound());
  }

  // 3. createStudent
  @Test
  void createStudent_ShouldReturnCreated() throws Exception {
    StudentRequestDTO request = StudentRequestDTO.builder()
        .name("John Doe")
        .pesel("12345678901")
        .phoneNumber("1234567890")
        .build();
    StudentResponseDTO response = StudentResponseDTO.builder()
        .id("123")
        .name("John Doe")
        .build();
    when(studentService.create(any())).thenReturn(response);

    mockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value("123"));
  }

  @Test
  void createStudent_ShouldReturnBadRequest_WhenInputIsInvalid() throws Exception {
    StudentRequestDTO invalidRequest = StudentRequestDTO.builder().build();

    mockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  // 4. updateStudent
  @Test
  void updateStudent_ShouldReturnOk() throws Exception {
    StudentRequestDTO request = StudentRequestDTO.builder()
        .id("123")
        .name("Updated John Doe")
        .pesel("12345678901")
        .phoneNumber("1234567890")
        .build();
    StudentResponseDTO response = StudentResponseDTO.builder()
        .id("123")
        .name("Updated John Doe")
        .build();
    when(studentService.update(any())).thenReturn(response);

    mockMvc.perform(put("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated John Doe"));
  }

  @Test
  void updateStudent_ShouldReturnNotFound_WhenStudentDoesNotExist() throws Exception {
    StudentRequestDTO request = StudentRequestDTO.builder()
        .id("non-existent")
        .name("Updated John Doe")
        .pesel("12345678901")
        .phoneNumber("1234567890")
        .build();
    when(studentService.update(any())).thenThrow(new com.app.dethloff.exceptions.model.StudentNotFoundException());

    mockMvc.perform(put("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  // 5. deleteStudent
  @Test
  void deleteStudent_ShouldReturnNoContent() throws Exception {
    String id = "123";

    mockMvc.perform(delete("/api/students/{studentId}", id))
        .andExpect(status().isNoContent());

    verify(studentService).delete(id);
  }

  @Test
  void deleteStudent_ShouldReturnNotFound_WhenStudentDoesNotExist() throws Exception {
    String id = "non-existent";
    doThrow(new com.app.dethloff.exceptions.model.StudentNotFoundException())
        .when(studentService).delete(id);

    mockMvc.perform(delete("/api/students/{studentId}", id))
        .andExpect(status().isNotFound());
  }
}
