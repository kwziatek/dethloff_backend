package com.app.dethloff.rest.mappers;

import com.app.dethloff.dao.SetOfCoursesDAO;
import com.app.dethloff.dao.StudentDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.*;
import com.app.dethloff.rest.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {

    private final StudentDAO studentDAO;
    private final TeacherDAO teacherDAO;
    private final SetOfCoursesDAO setOfCoursesDAO;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;
    private final SetOfCoursesMapper setOfCoursesMapper;

    @Autowired
    public CourseMapper(StudentDAO studentDAO, StudentMapper studentMapper, TeacherDAO teacherDAO, TeacherMapper teacherMapper, SetOfCoursesDAO setOfCoursesDAO, SetOfCoursesMapper setOfCoursesMapper) {
        this.studentDAO = studentDAO;
        this.studentMapper = studentMapper;
        this.teacherDAO = teacherDAO;
        this.teacherMapper = teacherMapper;
        this.setOfCoursesDAO = setOfCoursesDAO;
        this.setOfCoursesMapper = setOfCoursesMapper;
    }

    public CourseEntity toEntity(CourseRequestDTO courseRequestDTO) {
        TeacherEntity teacherProxy = null;
        if(courseRequestDTO.teacherId() != null) {
             teacherProxy = teacherDAO.createProxy(courseRequestDTO.teacherId());
        }

        SetOfCoursesEntity setOfCoursesProxy = null;
        if(courseRequestDTO.setOfCoursesId() != null) {
            setOfCoursesProxy = setOfCoursesDAO.createProxy(courseRequestDTO.setOfCoursesId());
        }
        List<StudentEntity> studentProxies = null;
        if(courseRequestDTO.studentIds() != null) {
            studentProxies = courseRequestDTO.studentIds().stream().map(studentDAO::createProxy).toList();
        }

        return CourseEntity.builder()
                .id(courseRequestDTO.id())
                .name(courseRequestDTO.name())
                .level(courseRequestDTO.level())
                .description(courseRequestDTO.description())
                .students(studentProxies)
                .teacher(teacherProxy)
                .setOfCourses(setOfCoursesProxy)
                .build();
    }

    public List<CourseEntity> toEntity(List<CourseRequestDTO> courseRequestDTOList) {
        return courseRequestDTOList.stream()
                .map(this::toEntity)
                .toList();
    }

    public CourseResponseDTO toCourseResponseDTO(CourseEntity course) {
        TeacherResponseDTO teacherResponseDTO = null;
        List<StudentResponseDTO> studentResponseDTOList = null;
        SetOfCoursesResponseDTO setOfCoursesRequestDTO = null;
        if(course.getTeacher() != null) {
            teacherResponseDTO = teacherMapper.entityToResponse(course.getTeacher());
        }
        if(course.getStudents() != null) {
            studentResponseDTOList = course.getStudents().stream().map(studentMapper::entityToResponse).toList();
        }
        if(course.getSetOfCourses() != null) {
            setOfCoursesRequestDTO = setOfCoursesMapper.entityToResponse(course.getSetOfCourses());
        }

        return CourseResponseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .level(course.getLevel())
                .description(course.getDescription())
                .teacher(teacherResponseDTO)
                .students(studentResponseDTOList)
                .setOfCourses(setOfCoursesRequestDTO)
                .room(course.getRoom())
                .totalCourseTime(course.getTotalCourseTime())
                .completedCourseTime(course.getCompletedCourseTime())
                .build();
    }

    public List<CourseResponseDTO> toCourseResponseDTO(List<CourseEntity> courseList) {
        return courseList.stream()
                .map(this::toCourseResponseDTO)
                .toList();
    }

    public void updateEntityFromRequest(CourseRequestDTO dto, CourseEntity entity) {
        if (dto == null) return;
        CourseEntity newCourseEntity = toEntity(dto);


        if(newCourseEntity.getName() != null) entity.setName(newCourseEntity.getName());
        if(newCourseEntity.getLevel() != null) entity.setLevel(newCourseEntity.getLevel());
        if(newCourseEntity.getDescription() != null) entity.setDescription(newCourseEntity.getDescription());
        if(newCourseEntity.getStudents() != null) entity.setStudents(newCourseEntity.getStudents());
        if(newCourseEntity.getTeacher() != null) entity.setTeacher(newCourseEntity.getTeacher());
        if(newCourseEntity.getSetOfCourses() != null) entity.setSetOfCourses(newCourseEntity.getSetOfCourses());
        if(newCourseEntity.getRoom() != null) entity.setRoom(newCourseEntity.getRoom());
        if(newCourseEntity.getTotalCourseTime() != null) entity.setTotalCourseTime(newCourseEntity.getTotalCourseTime());
        if(newCourseEntity.getCompletedCourseTime() != null) entity.setCompletedCourseTime(newCourseEntity.getCompletedCourseTime());
    }
}
