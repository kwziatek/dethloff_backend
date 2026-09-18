package com.app.dethloff.model.DTO.mappers;


import com.app.dethloff.dao.SetOfCoursesDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.DTO.*;
import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.model.SetOfCoursesEntity;
import com.app.dethloff.model.TeacherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class CourseMapper {

    TeacherDAO teacherDAO;
    SetOfCoursesDAO setOfCoursesDAO;
    TeacherMapper teacherMapper;
    StudentMapper studentMapper;
    SetOfCoursesMapper setOfCoursesMapper;


    @Autowired
    public CourseMapper(TeacherDAO teacherDAO, TeacherMapper teacherMapper, StudentMapper studentMapper, SetOfCoursesDAO setOfCoursesDAO, SetOfCoursesMapper setOfCoursesMapper) {
        this.teacherDAO = teacherDAO;
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
        this.setOfCoursesDAO = setOfCoursesDAO;
        this.setOfCoursesMapper = setOfCoursesMapper;
    }

    public DetailedCourseDTO toDetailedDTO(CourseEntity course) {
        BasicTeacherDTO basicTeacherDTO = null;
        List<BasicStudentDTO> basicStudentDTOs = null;
        SetOfCoursesDTO setOfCoursesDTO = null;
        if(course.getTeacher() != null) {
            basicTeacherDTO = teacherMapper.toBasicDTO(course.getTeacher());
        }
        if(course.getStudents() != null) {
            basicStudentDTOs = studentMapper.toBasicDTO(course.getStudents());
        }
        if(course.getSetOfCourses() != null) {
            setOfCoursesDTO = setOfCoursesMapper.toDTO(course.getSetOfCourses());
        }


        return DetailedCourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .level(course.getLevel())
                .description(course.getDescription())
                .teacher(basicTeacherDTO)
                .students(basicStudentDTOs)
                .setOfCourses(setOfCoursesDTO)
                .build();
    }

    public CourseEntity basicToEntity(BasicCourseDTO basicCourseDTO) {
        TeacherEntity teacherProxy = null;
        if(basicCourseDTO.teacherId() != null) {
             teacherProxy = teacherDAO.createProxy(basicCourseDTO.teacherId());
        }

        SetOfCoursesEntity setOfCoursesProxy = null;
        if(basicCourseDTO.setOfCoursesId() != null) {
            setOfCoursesProxy = setOfCoursesDAO.createProxy(basicCourseDTO.setOfCoursesId());
        }


        return CourseEntity.builder()
                .id(basicCourseDTO.id())
                .name(basicCourseDTO.name())
                .level(basicCourseDTO.level())
                .description(basicCourseDTO.description())
                .teacher(teacherProxy)
                .setOfCourses(setOfCoursesProxy)
                .build();
    }

    public List<DetailedCourseDTO> toDetailedDTO(List<CourseEntity> courseList) {
        return courseList.stream()
                .map(this::toDetailedDTO)
                .toList();
    }

    public List<CourseEntity> basicToEntity(List<BasicCourseDTO> basicCourseDTOList) {
        return basicCourseDTOList.stream()
                .map(this::basicToEntity)
                .toList();
    }
}
