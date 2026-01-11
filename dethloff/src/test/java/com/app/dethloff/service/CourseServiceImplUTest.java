package com.app.dethloff.service;

import com.app.dethloff.DAO.CourseDAO;
import com.app.dethloff.DAO.StudentDAO;
import com.app.dethloff.DTO.CourseDTO;
import com.app.dethloff.DTO.mappers.CourseMapper;
import com.app.dethloff.model.Course;
import com.app.dethloff.model.CourseLevel;
import com.app.dethloff.model.Student;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplUTest {

    @Mock
    private CourseDAO courseDAO;

    @Mock
    private StudentDAO studentDAO;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course sampleCourse;
    private Student sampleStudent;

    @BeforeEach
     void setUp() {
        UUID studentId = UUID.randomUUID();
        UUID courseID = UUID.randomUUID();
        sampleCourse = new Course(courseID.toString(), CourseLevel.A1, "NICE", new ArrayList<>());
        sampleStudent = Student.builder()
                .id(studentId.toString())
                .name("Karol")
                .surname("Piątkowski")
                .courses(new ArrayList<>())
                .build();
    }

    @Test
    void getCourse_shouldReturnDTO_whenIdExists() {
        // arrange
        when(courseDAO.findById(sampleCourse.getId())).thenReturn(Optional.ofNullable(sampleCourse));

        // act
        CourseDTO courseDTO = courseService.get(sampleCourse.getId());

        // assert
        assertNotNull(courseDTO);
        assertEquals(sampleCourse.getId(), courseDTO.id());
    }

    @Test
    void getCourse_CourseNotFound_ThrowsException() {
        // arrange
        when(courseDAO.findById("NON-EXISTENT")).thenReturn(Optional.empty());

        // act & arrange
        assertThrows(RuntimeException.class, () -> courseService.get("NON-EXISTENT"));

        // verify
        verify(courseDAO).findById("NON-EXISTENT");
    }

    @Test
    void createCourse_ShouldSaveAndReturnDTO() {

    }

    @Test
    void deleteCourse_ShouldInvokeDeleteOnDAO() {
        // arrange
        when(courseDAO.findById(sampleCourse.getId())).thenReturn(Optional.ofNullable(sampleCourse));

        // act
        courseService.delete(sampleCourse.getId());

        // assert
        verify(courseDAO).remove(sampleCourse);
    }

    @Test
    void testEnrollStudent_Success() {
        // arrange
        when(courseDAO.findById(sampleCourse.getId())).thenReturn(Optional.ofNullable(sampleCourse));
        when(studentDAO.findById(sampleStudent.getId())).thenReturn(Optional.ofNullable(sampleStudent));

        // act
        courseService.enrollStudent(sampleCourse.getId(),sampleStudent.getId());

        // assert
        assertTrue(sampleCourse.getStudents().contains(sampleStudent));
        assertTrue(sampleStudent.getCourses().contains(sampleCourse));

        // verify
        verify(courseDAO).findById(sampleCourse.getId());
        verify(studentDAO).findById(sampleStudent.getId());
    }

    @Test
    void testUnenrollStudent_Success() {
        // arrange
        when(courseDAO.findById(sampleCourse.getId())).thenReturn(Optional.ofNullable(sampleCourse));
        when(studentDAO.findById(sampleStudent.getId())).thenReturn(Optional.ofNullable(sampleStudent));

        // act
        courseService.unenrollStudent(sampleCourse.getId(), sampleStudent.getId());

        // assert
        assertFalse(sampleCourse.getStudents().contains(sampleStudent));
        assertFalse(sampleStudent.getCourses().contains(sampleCourse));

        // verify
        verify(courseDAO).findById(sampleCourse.getId());
        verify(studentDAO).findById(sampleStudent.getId());
    }

    @Test
    void testEnrollStudent_CourseNotFound_ThrowsException() {
        // arrange
        when(courseDAO.findById("NON-EXISTENT")).thenReturn(Optional.empty());

        // act && assert
        assertThrows(RuntimeException.class, () -> courseService.enrollStudent("NON-EXISTENT", any()));

        // verify studentDAO was never called because course failed first
        verifyNoInteractions(studentDAO);
        verify(courseDAO).findById("NON-EXISTENT");
    }
}
