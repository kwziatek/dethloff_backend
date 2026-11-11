package com.app.dethloff.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.dethloff.model.TeacherEntity;
import com.app.dethloff.rest.DTO.TeacherRequestDTO;
import com.app.dethloff.rest.DTO.TeacherResponseDTO;
import com.app.dethloff.service.inte.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.mvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {

  @Autowired private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @MockitoBean
  private TeacherService teacherService;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  // 1. getTeacher
  @Test
  void getTeacher_ShouldReturnTeacher() throws Exception {
    String teacherId = "1";
    TeacherResponseDTO teacher = TeacherResponseDTO.builder().id(teacherId).name("John Doe").build();
    when(teacherService.get(teacherId)).thenReturn(teacher);

    mockMvc.perform(get("/api/teachers/{teacherId}", teacherId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(teacherId));
  }

  @Test
  void getTeacher_ShouldReturnNotFound_WhenTeacherDoesNotExist() throws Exception {
    String teacherId = "non-existent";
    when(teacherService.get(teacherId)).thenThrow(new com.app.dethloff.exceptions.model.TeacherNotFoundException());

    mockMvc.perform(get("/api/teachers/{teacherId}", teacherId))
        .andExpect(status().isNotFound());
  }

  // 2. getAllTeacher
  @Test
  void getAllTeacher_ShouldReturnList() throws Exception {
    TeacherResponseDTO teacher = TeacherResponseDTO.builder().id("1").name("John Doe").build();
    when(teacherService.getAll()).thenReturn(List.of(teacher));

    mockMvc.perform(get("/api/teachers"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value("1"));
  }

  @Test
  void getAllTeacher_ShouldReturnInternalServerError_WhenServiceFails() throws Exception {
    when(teacherService.getAll()).thenThrow(new RuntimeException("Database error"));

    mockMvc.perform(get("/api/teachers"))
        .andExpect(status().isInternalServerError());
  }

  // 3. createTeacher
  @Test
  void createTeacher_ShouldReturnCreated() throws Exception {
    TeacherRequestDTO request = TeacherRequestDTO.builder()
        .name("John Doe")
        .pesel("12345678901")
        .phoneNumber("1234567890")
        .build();
    TeacherResponseDTO response = TeacherResponseDTO.builder()
        .id("123")
        .name("John Doe")
        .build();
    when(teacherService.create(any())).thenReturn(response);

    mockMvc.perform(post("/api/teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value("123"));
  }

  @Test
  void createTeacher_ShouldReturnBadRequest_WhenInputIsInvalid() throws Exception {
    TeacherRequestDTO invalidRequest = TeacherRequestDTO.builder().build();

    mockMvc.perform(post("/api/teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  // 4. updateTeacher
  @Test
  void updateTeacher_ShouldReturnOk() throws Exception {
    TeacherRequestDTO request = TeacherRequestDTO.builder()
        .id("123")
        .name("Updated John Doe")
        .pesel("12345678901")
        .phoneNumber("1234567890")
        .build();
    TeacherResponseDTO response = TeacherResponseDTO.builder()
        .id("123")
        .name("Updated John Doe")
        .build();
    when(teacherService.update(any())).thenReturn(response);

    mockMvc.perform(put("/api/teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated John Doe"));
  }

  @Test
  void updateTeacher_ShouldReturnNotFound_WhenTeacherDoesNotExist() throws Exception {
    TeacherRequestDTO request = TeacherRequestDTO.builder()
        .id("non-existent")
        .name("Updated John Doe")
        .pesel("12345678901")
        .phoneNumber("1234567890")
        .build();
    when(teacherService.update(any())).thenThrow(new com.app.dethloff.exceptions.model.TeacherNotFoundException());

    mockMvc.perform(put("/api/teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  // 5. deleteTeacher
  @Test
  void deleteTeacher_ShouldReturnNoContent() throws Exception {
    String teacherId = "123";

    mockMvc.perform(delete("/api/teachers/{teacherId}", teacherId))
        .andExpect(status().isNoContent());

    verify(teacherService).delete(teacherId);
  }

  @Test
  void deleteTeacher_ShouldReturnNotFound_WhenTeacherDoesNotExist() throws Exception {
    String teacherId = "non-existent";
    doThrow(new com.app.dethloff.exceptions.model.TeacherNotFoundException())
        .when(teacherService).delete(teacherId);

    mockMvc.perform(delete("/api/teachers/{teacherId}", teacherId))
        .andExpect(status().isNotFound());
  }
}
