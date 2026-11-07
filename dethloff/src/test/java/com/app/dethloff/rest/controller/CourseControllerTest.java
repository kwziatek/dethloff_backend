package com.app.dethloff.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.dethloff.model.CourseLevel;
import com.app.dethloff.rest.DTO.CourseRequestDTO;
import com.app.dethloff.rest.DTO.CourseResponseDTO;
import com.app.dethloff.rest.DTO.SetOfCoursesResponseDTO;
import com.app.dethloff.service.impl.JWTServiceImpl;
import com.app.dethloff.service.inte.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

  @Autowired private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @MockitoBean
  private CourseService courseService;

  @MockitoBean
  private JWTServiceImpl jwtServiceImpl;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  // 1. getAllCourses
  @Test
  void getAllCourses_ShouldReturnList() throws Exception {
    CourseResponseDTO course = CourseResponseDTO.builder().id("1").description("Test").build();
    when(courseService.getAll()).thenReturn(List.of(course));

    mockMvc.perform(get("/api/courses"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value("1"));
  }

  @Test
  void getAllCourses_ShouldReturnInternalServerError_WhenServiceFails() throws Exception {
    when(courseService.getAll()).thenThrow(new RuntimeException("Database error"));

    mockMvc.perform(get("/api/courses"))
        .andExpect(status().isInternalServerError());
  }

  // 2. getAllSetCourses
  @Test
  void getAllSetCourses_ShouldReturnList() throws Exception {
    String setId = "set1";
    CourseResponseDTO course = CourseResponseDTO.builder().id("1").description("Test").build();
    when(courseService.getAllFromParticularSet(setId)).thenReturn(List.of(course));

    mockMvc.perform(get("/api/courses/setsOfCourses/{setOfCoursesId}", setId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value("1"));
  }

  @Test
  void getAllSetCourses_ShouldReturnNotFound_WhenSetDoesNotExist() throws Exception {
    String setId = "non-existent";
    when(courseService.getAllFromParticularSet(setId)).thenThrow(new com.app.dethloff.exceptions.model.CourseNotFoundException());

    mockMvc.perform(get("/api/courses/setsOfCourses/{setOfCoursesId}", setId))
        .andExpect(status().isNotFound());
  }

  // 3. getAllSetsOfCourses
  @Test
  void getAllSetsOfCourses_ShouldReturnList() throws Exception {
    SetOfCoursesResponseDTO set = SetOfCoursesResponseDTO.builder().id("set1").name("Java Sets").build();
    when(courseService.getAllSetsOfCourses()).thenReturn(List.of(set));

    mockMvc.perform(get("/api/courses/setsOfCourses"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value("set1"));
  }

  @Test
  void getAllSetsOfCourses_ShouldReturnInternalServerError_WhenServiceFails() throws Exception {
    when(courseService.getAllSetsOfCourses()).thenThrow(new RuntimeException("Service failure"));

    mockMvc.perform(get("/api/courses/setsOfCourses"))
        .andExpect(status().isInternalServerError());
  }

  // 4. getCourse
  @Test
  void getCourse_ShouldReturnCourse() throws Exception {
    String id = "1";
    CourseResponseDTO course = CourseResponseDTO.builder().id(id).description("Test").build();
    when(courseService.get(id)).thenReturn(course);

    mockMvc.perform(get("/api/courses/{courseId}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id));
  }

  @Test
  void getCourse_ShouldReturnNotFound_WhenCourseDoesNotExist() throws Exception {
    String id = "non-existent";
    when(courseService.get(id)).thenThrow(new com.app.dethloff.exceptions.model.CourseNotFoundException());

    mockMvc.perform(get("/api/courses/{courseId}", id))
        .andExpect(status().isNotFound());
  }

  // 5. createCourse
  @Test
  void createCourse_ShouldReturnCreated() throws Exception {
    CourseRequestDTO request = CourseRequestDTO.builder()
        .name("Java Course")
        .level(CourseLevel.A1)
        .description("New Course")
        .studentIds(List.of("s1", "s2"))
        .teacherId("t1")
        .setOfCoursesId("set1")
        .room("Room 101")
        .totalCourseTime(100)
        .completedCourseTime(0)
        .build();
    CourseResponseDTO response = CourseResponseDTO.builder()
        .id("123")
        .description("New Course")
        .build();
    when(courseService.create(any())).thenReturn(response);

    mockMvc.perform(post("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value("123"));
  }

  @Test
  void createCourse_ShouldReturnBadRequest_WhenInputIsInvalid() throws Exception {
    CourseRequestDTO invalidRequest = CourseRequestDTO.builder().build();

    mockMvc.perform(post("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  // 6. enrollStudent
  @Test
  void enrollStudent_ShouldReturnNoContent() throws Exception {
    String courseId = "c1";
    String studentId = "s1";

    mockMvc.perform(post("/api/courses/{courseId}/students/{studentId}", courseId, studentId))
        .andExpect(status().isNoContent());

    verify(courseService).enrollStudent(courseId, studentId);
  }

  @Test
  void enrollStudent_ShouldReturnBadRequest_WhenStudentAlreadyEnrolled() throws Exception {
    String courseId = "c1";
    String studentId = "s1";
    doThrow(new com.app.dethloff.exceptions.model.StudentAlreadyEnrolledException())
        .when(courseService).enrollStudent(courseId, studentId);

    mockMvc.perform(post("/api/courses/{courseId}/students/{studentId}", courseId, studentId))
        .andExpect(status().isBadRequest());
  }

  // 7. updateCourse
  @Test
  void updateCourse_ShouldReturnOk() throws Exception {
    CourseRequestDTO request = CourseRequestDTO.builder()
        .id("123")
        .name("Updated Course")
        .level(CourseLevel.A1)
        .description("Updated")
        .studentIds(List.of("s1"))
        .teacherId("t1")
        .setOfCoursesId("set1")
        .room("Room 101")
        .totalCourseTime(100)
        .completedCourseTime(10)
        .build();
    CourseResponseDTO response = CourseResponseDTO.builder()
        .id("123")
        .description("Updated")
        .build();
    when(courseService.update(any())).thenReturn(response);

    mockMvc.perform(put("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.description").value("Updated"));
  }

  @Test
  void updateCourse_ShouldReturnNotFound_WhenCourseDoesNotExist() throws Exception {
    CourseRequestDTO request = CourseRequestDTO.builder()
        .id("non-existent")
        .name("Updated Course")
        .level(CourseLevel.A1)
        .description("Updated")
        .studentIds(List.of("s1"))
        .teacherId("t1")
        .setOfCoursesId("set1")
        .room("Room 101")
        .totalCourseTime(100)
        .completedCourseTime(10)
        .build();
    when(courseService.update(any())).thenThrow(new com.app.dethloff.exceptions.model.CourseNotFoundException());

    mockMvc.perform(put("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  // 8. deleteCourse
  @Test
  void deleteCourse_ShouldReturnNoContent() throws Exception {
    String id = "123";

    mockMvc.perform(delete("/api/courses/{courseId}", id))
        .andExpect(status().isNoContent());

    verify(courseService).delete(id);
  }

  @Test
  void deleteCourse_ShouldReturnNotFound_WhenCourseDoesNotExist() throws Exception {
    String id = "non-existent";
    doThrow(new com.app.dethloff.exceptions.model.CourseNotFoundException())
        .when(courseService).delete(id);

    mockMvc.perform(delete("/api/courses/{courseId}", id))
        .andExpect(status().isNotFound());
  }

  // 9. unenrollStudent
  @Test
  void unenrollStudent_ShouldReturnNoContent() throws Exception {
    String courseId = "c1";
    String studentId = "s1";

    mockMvc.perform(delete("/api/courses/{courseId}/students/{studentId}", courseId, studentId))
        .andExpect(status().isNoContent());

    verify(courseService).unenrollStudent(courseId, studentId);
  }

  @Test
  void unenrollStudent_ShouldReturnNotFound_WhenCourseOrStudentDoesNotExist() throws Exception {
    String courseId = "non-existent";
    String studentId = "s1";
    doThrow(new com.app.dethloff.exceptions.model.CourseNotFoundException())
        .when(courseService).unenrollStudent(courseId, studentId);

    mockMvc.perform(delete("/api/courses/{courseId}/students/{studentId}", courseId, studentId))
        .andExpect(status().isNotFound());
  }
}
