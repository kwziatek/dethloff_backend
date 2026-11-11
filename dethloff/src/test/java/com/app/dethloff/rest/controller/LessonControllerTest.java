package com.app.dethloff.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.dethloff.model.LessonEntity;
import com.app.dethloff.rest.DTO.LessonRequestDTO;
import com.app.dethloff.rest.DTO.LessonResponseDTO;
import com.app.dethloff.service.inte.CourseService;
import com.app.dethloff.service.inte.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.mvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LessonController.class)
public class LessonControllerTest {

  @Autowired private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @MockitoBean
  private LessonService lessonService;

  @MockitoBean
  private CourseService courseService;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  // 1. getLesson
  @Test
  void getLesson_ShouldReturnLesson() throws Exception {
    String lessonId = "1";
    LessonResponseDTO lesson = LessonResponseDTO.builder().id(lessonId).description("Test").build();
    when(lessonService.get(lessonId)).thenReturn(lesson);

    mockMvc.perform(get("/api/lesson/{lessonId}", lessonId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(lessonId));
  }

  @Test
  void getLesson_ShouldReturnNotFound_WhenLessonDoesNotExist() throws Exception {
    String lessonId = "non-existent";
    when(lessonService.get(lessonId)).thenThrow(new com.app.dethloff.exceptions.model.LessonNotFoundException());

    mockMvc.perform(get("/api/lesson/{lessonId}", lessonId))
        .andExpect(status().isNotFound());
  }

  // 2. getLessonsAssignedToCourse
  @Test
  void getLessonsAssignedToCourse_ShouldReturnList() throws Exception {
    String courseId = "c1";
    LessonResponseDTO lesson = LessonResponseDTO.builder().id("1").description("Test").build();
    when(lessonService.getAllFromCourse(courseId)).thenReturn(List.of(lesson));

    mockMvc.perform(get("/api/lesson/course/{courseId}", courseId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value("1"));
  }

  @Test
  void getLessonsAssignedToCourse_ShouldReturnNotFound_WhenCourseDoesNotExist() throws Exception {
    String courseId = "non-existent";
    when(lessonService.getAllFromCourse(courseId)).thenThrow(new com.app.dethloff.exceptions.model.CourseNotFoundException());

    mockMvc.perform(get("/api/lesson/course/{courseId}", courseId))
        .andExpect(status().isNotFound());
  }

  // 3. getAllLessons
  @Test
  void getAllLessons_ShouldReturnList() throws Exception {
    LessonResponseDTO lesson = LessonResponseDTO.builder().id("1").description("Test").build();
    when(lessonService.getAll()).thenReturn(List.of(lesson));

    mockMvc.perform(get("/api/lesson"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value("1"));
  }

  @Test
  void getAllLessons_ShouldReturnInternalServerError_WhenServiceFails() throws Exception {
    when(lessonService.getAll()).thenThrow(new RuntimeException("Database error"));

    mockMvc.perform(get("/api/lesson"))
        .andExpect(status().isInternalServerError());
  }

  // 4. createLesson
  @Test
  void createLesson_ShouldReturnCreated() throws Exception {
    LessonRequestDTO request = LessonRequestDTO.builder()
        .courseId("c1")
        .description("Test")
        .startTime("2023-04-01T10:00:00")
        .endTime("2023-04-01T11:00:00")
        .build();
    LessonResponseDTO response = LessonResponseDTO.builder()
        .id("123")
        .description("Test")
        .build();
    when(lessonService.create(any())).thenReturn(response);

    mockMvc.perform(post("/api/lesson")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value("123"));
  }

  @Test
  void createLesson_ShouldReturnBadRequest_WhenInputIsInvalid() throws Exception {
    LessonRequestDTO invalidRequest = LessonRequestDTO.builder().build();

    mockMvc.perform(post("/api/lesson")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  // 5. updateLesson
  @Test
  void updateLesson_ShouldReturnOk() throws Exception {
    LessonRequestDTO request = LessonRequestDTO.builder()
        .id("123")
        .courseId("c1")
        .description("Updated Test")
        .startTime("2023-04-01T10:00:00")
        .endTime("2023-04-01T11:00:00")
        .build();
    LessonResponseDTO response = LessonResponseDTO.builder()
        .id("123")
        .description("Updated Test")
        .build();
    when(lessonService.update(any())).thenReturn(response);

    mockMvc.perform(put("/api/lesson")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.description").value("Updated Test"));
  }

  @Test
  void updateLesson_ShouldReturnNotFound_WhenLessonDoesNotExist() throws Exception {
    LessonRequestDTO request = LessonRequestDTO.builder()
        .id("non-existent")
        .courseId("c1")
        .description("Updated Test")
        .startTime("2023-04-01T10:00:00")
        .endTime("2023-04-01T11:00:00")
        .build();
    when(lessonService.update(any())).thenThrow(new com.app.dethloff.exceptions.model.LessonNotFoundException());

    mockMvc.perform(put("/api/lesson")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  // 6. deleteLesson
  @Test
  void deleteLesson_ShouldReturnNoContent() throws Exception {
    String lessonId = "123";

    mockMvc.perform(delete("/api/lesson/{lessonId}", lessonId))
        .andExpect(status().isNoContent());

    verify(lessonService).delete(lessonId);
  }

  @Test
  void deleteLesson_ShouldReturnNotFound_WhenLessonDoesNotExist() throws Exception {
    String lessonId = "non-existent";
    doThrow(new com.app.dethloff.exceptions.model.LessonNotFoundException())
        .when(lessonService).delete(lessonId);

    mockMvc.perform(delete("/api/lesson/{lessonId}", lessonId))
        .andExpect(status().isNotFound());
  }
}
