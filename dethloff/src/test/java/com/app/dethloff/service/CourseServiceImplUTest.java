package com.app.dethloff.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplUTest {

//    @Mock
//    private CourseDAO courseDAO;
//
//    @Mock
//    private StudentDAO studentDAO;
//
//    @InjectMocks
//    private CourseServiceImpl courseService;
//
//    @Mock
//    private CourseMapper courseMapper;
//
//    private CourseEntity sampleCourse;
//    private StudentEntity sampleStudent;
//    private TeacherEntity sampleTeacher;
//
//    @BeforeEach
//     void setUp() {
//        UUID studentId = UUID.randomUUID();
//        UUID courseId = UUID.randomUUID();
//        UUID teacherID = UUID.randomUUID();
//
//        sampleStudent = StudentEntity.builder()
//                .id(studentId.toString())
//                .name("Karol")
//                .surname("Piątkowski")
//                .courses(new ArrayList<>())
//                .build();
//        sampleTeacher = TeacherEntity.builder()
//                .id(teacherID.toString())
//                .name("Michał")
//                .surname("Żarowski")
//                .courses(new ArrayList<>())
//                .build();
//
////        sampleCourse = new CourseEntity(courseId.toString(), "A1_1", CourseLevel.A1, "NICE", new ArrayList<>(), sampleTeacher);
//
//        sampleCourse = CourseEntity.builder()
//                .id(courseId.toString())
//                .level(CourseLevel.A1)
//                .description("NICE")
//                .students(new ArrayList<>())
//                .teacher(sampleTeacher)
//                .build();
//    }
//
//    @Test
//    void getCourse_shouldReturnDTO_whenIdExists() {
//        // arrange
//        when(courseDAO.findById(sampleCourse.getId())).thenReturn(Optional.ofNullable(sampleCourse));
//        when(courseMapper.toCourseResponseDTO(sampleCourse)).thenReturn(CourseResponseDTO.builder()
//                        .id(sampleCourse.getId())
//                        .name(sampleCourse.getName())
//                        .description(sampleCourse.getDescription())
//                        .level(sampleCourse.getLevel())
//                        .students(null)
//                        .teacher(null) // teacherMapper would need to be mocked
//                        .build()
//        );
//
//        // act
//        CourseResponseDTO courseResponseDTO = courseService.get(sampleCourse.getId());
//
//        // assert
//        assertNotNull(courseResponseDTO);
//        assertEquals(sampleCourse.getId(), courseResponseDTO.id());
//    }
//
//    @Test
//    void getCourse_CourseNotFound_ThrowsException() {
//        // arrange
//        when(courseDAO.findById("NON-EXISTENT")).thenReturn(Optional.empty());
//
//        // act with arrange
//        assertThrows(RuntimeException.class, () -> courseService.get("NON-EXISTENT"));
//
//        // verify
//        verify(courseDAO).findById("NON-EXISTENT");
//    }
//
//    @Test
//    void createCourse_ShouldSaveAndReturnDTO() {
//
//    }
//
//    @Test
//    void deleteCourse_ShouldInvokeDeleteOnDAO() {
//        // arrange
//        when(courseDAO.findById(sampleCourse.getId())).thenReturn(Optional.ofNullable(sampleCourse));
//
//        // act
//        courseService.delete(sampleCourse.getId());
//
//        // assert
//        verify(courseDAO).remove(sampleCourse);
//    }
//
//    @Test
//    void testEnrollStudent_Success() {
//        // arrange
//        when(courseDAO.findById(sampleCourse.getId())).thenReturn(Optional.ofNullable(sampleCourse));
//        when(studentDAO.findById(sampleStudent.getId())).thenReturn(Optional.ofNullable(sampleStudent));
//
//        // act
//        courseService.enrollStudent(sampleCourse.getId(),sampleStudent.getId());
//
//        // assert
//        assertTrue(sampleCourse.getStudents().contains(sampleStudent));
//        assertTrue(sampleStudent.getCourses().contains(sampleCourse));
//
//        // verify
//        verify(courseDAO).findById(sampleCourse.getId());
//        verify(studentDAO).findById(sampleStudent.getId());
//    }
//
//    @Test
//    void testUnenrollStudent_Success() {
//        // arrange
//        when(courseDAO.findById(sampleCourse.getId())).thenReturn(Optional.ofNullable(sampleCourse));
//        when(studentDAO.findById(sampleStudent.getId())).thenReturn(Optional.ofNullable(sampleStudent));
//
//        // act
//        courseService.unenrollStudent(sampleCourse.getId(), sampleStudent.getId());
//
//        // assert
//        assertFalse(sampleCourse.getStudents().contains(sampleStudent));
//        assertFalse(sampleStudent.getCourses().contains(sampleCourse));
//
//        // verify
//        verify(courseDAO).findById(sampleCourse.getId());
//        verify(studentDAO).findById(sampleStudent.getId());
//    }
//
//    @Test
//    void testEnrollStudent_CourseNotFound_ThrowsException() {
//        // arrange
//        when(courseDAO.findById("NON-EXISTENT")).thenReturn(Optional.empty());
//
//        // act && assert
//        assertThrows(RuntimeException.class, () -> courseService.enrollStudent("NON-EXISTENT", any()));
//
//        // verify studentDAO was never called because course failed first
//        verifyNoInteractions(studentDAO);
//        verify(courseDAO).findById("NON-EXISTENT");
//    }
}
