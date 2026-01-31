package com.app.dethloff.model.DTO.mappers;


import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.DTO.CourseRequestDTO;
import com.app.dethloff.model.Course;
import com.app.dethloff.model.DTO.CourseResponseDTO;
import com.app.dethloff.model.DTO.StudentResponseDTO;
import com.app.dethloff.model.DTO.TeacherResponseDTO;
import com.app.dethloff.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class CourseMapper {

    TeacherDAO teacherDAO;
    TeacherMapper teacherMapper;
    StudentMapper studentMapper;


    @Autowired
    public CourseMapper(TeacherDAO teacherDAO, TeacherMapper teacherMapper, StudentMapper studentMapper) {
        this.teacherDAO = teacherDAO;
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
    }

    public CourseResponseDTO toDTO(Course course) {
        TeacherResponseDTO teacherResponseDTO = teacherMapper.toDTO(course.getTeacher());
        List<StudentResponseDTO> studentResponseDTOs = studentMapper.toDTO(course.getStudents());

        return CourseResponseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .level(course.getLevel())
                .description(course.getDescription())
                .teacher(teacherResponseDTO)
                .students(studentResponseDTOs)
                .build();
    }

    public Course toCourse(CourseRequestDTO courseRequestDTO) {
        Teacher teacherProxy = teacherDAO.createProxy(courseRequestDTO.teacherId());

        return Course.builder()
                .id(courseRequestDTO.id())
                .name(courseRequestDTO.name())
                .level(courseRequestDTO.level())
                .description(courseRequestDTO.description())
                .teacher(teacherProxy)
                .build();
    }

    public List<CourseResponseDTO> toDTO(List<Course> courseList) {
        return courseList.stream()
                .map(this::toDTO)
                .toList();
    }

    public List<Course> toCourse(List<CourseRequestDTO> courseRequestDTOList) {
        return courseRequestDTOList.stream()
                .map(this::toCourse)
                .toList();
    }
}
