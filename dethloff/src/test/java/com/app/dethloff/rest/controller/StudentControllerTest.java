package com.app.dethloff.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.dethloff.exceptions.model.StudentNotFoundException;
import com.app.dethloff.model.Gender;
import com.app.dethloff.rest.DTO.StudentRequestDTO;
import com.app.dethloff.rest.DTO.StudentResponseDTO;
import com.app.dethloff.service.impl.JWTServiceImpl;
import com.app.dethloff.service.inte.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

  @Autowired private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @MockitoBean private StudentService studentService;

  @MockitoBean private JWTServiceImpl jwtServiceImpl;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  // 1. getStudents
  @Test
  void getStudents_ShouldReturnList() throws Exception {
    StudentResponseDTO student1 =
        StudentResponseDTO.builder().id("1").name("John").surname("Doe").build();
    StudentResponseDTO student2 =
        StudentResponseDTO.builder().id("2").name("Jane").surname("Doe").build();
    when(studentService.getAll()).thenReturn(List.of(student1, student2));

    mockMvc
        .perform(get("/api/students"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value("1"))
        .andExpect(jsonPath("$[1].id").value("2"));
  }

  // 2. getStudent
  @Test
  void getStudent_ShouldReturnStudent() throws Exception {
    String studentId = "1";
    StudentResponseDTO student =
        StudentResponseDTO.builder().id(studentId).name("John").surname("Doe").build();
    when(studentService.get(studentId)).thenReturn(student);

    mockMvc
        .perform(get("/api/students/{studentId}", studentId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(studentId));
  }

  @Test
  void getStudent_ShouldReturnNotFound_WhenStudentDoesNotExist() throws Exception {
    String studentId = "non-existent";
    when(studentService.get(studentId)).thenThrow(new StudentNotFoundException());

    mockMvc.perform(get("/api/students/{studentId}", studentId)).andExpect(status().isNotFound());
  }

  // 3. createStudent
  @Test
  void createStudent_ShouldReturnCreated() throws Exception {
    StudentRequestDTO request =
        StudentRequestDTO.builder()
            .name("John")
            .surname("Doe")
            .pesel("12345678901")
            .placeOfBirth("Warsaw")
            .birthDate(LocalDate.of(1990, 1, 1))
            .gender(Gender.MALE)
            .city("Warsaw")
            .street("Main St")
            .flatNumber("1")
            .postalCode("00-001")
            .phoneNumber("+48 123 456 789")
            .email("john.doe@example.com")
            .build();
    StudentResponseDTO response =
        StudentResponseDTO.builder().id("123").name("John").surname("Doe").build();
    when(studentService.create(any())).thenReturn(response);

    mockMvc
        .perform(
            post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value("123"));
  }

  @Test
  void createStudent_ShouldReturnBadRequest_WhenInputIsInvalid() throws Exception {
    StudentRequestDTO invalidRequest = StudentRequestDTO.builder().build();

    mockMvc
        .perform(
            post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  // 4. updateStudent
  @Test
  void updateStudent_ShouldReturnOk() throws Exception {
    StudentRequestDTO request =
        StudentRequestDTO.builder()
            .id("123")
            .name("Updated John")
            .surname("Doe")
            .pesel("12345678901")
            .placeOfBirth("Warsaw")
            .birthDate(LocalDate.of(1990, 1, 1))
            .gender(Gender.MALE)
            .city("Warsaw")
            .street("Main St")
            .flatNumber("1")
            .postalCode("00-001")
            .phoneNumber("+48 123 456 789")
            .email("updated.john.doe@example.com")
            .build();
    StudentResponseDTO response =
        StudentResponseDTO.builder().id("123").name("Updated John").surname("Doe").build();
    when(studentService.update(any())).thenReturn(response);

    mockMvc
        .perform(
            put("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.surname").value("Doe"));
  }

  @Test
  void updateStudent_ShouldReturnNotFound_WhenStudentDoesNotExist() throws Exception {
    StudentRequestDTO request =
        StudentRequestDTO.builder()
            .id("non-existent")
            .name("Updated John Doe")
            .pesel("12345678901")
            .placeOfBirth("Warsaw")
            .birthDate(LocalDate.of(1990, 1, 1))
            .gender(Gender.MALE)
            .city("Warsaw")
            .street("Main St")
            .flatNumber("1")
            .postalCode("00-001")
            .phoneNumber("+48 123 456 789")
            .email("updated.john.doe@example.com")
            .build();
    when(studentService.update(any())).thenThrow(new StudentNotFoundException());

    mockMvc
        .perform(
            put("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  // 5. deleteStudent
  @Test
  void deleteStudent_ShouldReturnNoContent() throws Exception {
    String studentId = "123";

    mockMvc
        .perform(delete("/api/students/{studentId}", studentId))
        .andExpect(status().isNoContent());

    verify(studentService).delete(studentId);
  }

  @Test
  void deleteStudent_ShouldReturnNotFound_WhenStudentDoesNotExist() throws Exception {
    String studentId = "non-existent";
    doThrow(new StudentNotFoundException()).when(studentService).delete(studentId);

    mockMvc
        .perform(delete("/api/students/{studentId}", studentId))
        .andExpect(status().isNotFound());
  }
}
