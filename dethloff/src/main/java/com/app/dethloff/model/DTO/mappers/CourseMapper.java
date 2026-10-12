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

    private final TeacherDAO teacherDAO;
    private final SetOfCoursesDAO setOfCoursesDAO;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;
    private final SetOfCoursesMapper setOfCoursesMapper;

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
                .room(course.getRoom())
                .totalCourseTime(course.getTotalCourseTime())
                .completedCourseTime(course.getCompletedCourseTime())
                .build();
    }

    public CourseEntity toEntity(BasicCourseDTO basicCourseDTO) {
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

    public BasicCourseDTO toBasicDTO(CourseEntity course) {
        return BasicCourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .teacherId(course.getTeacher().getId())
                .setOfCoursesId(course.getSetOfCourses().getId())
                .build();
    }

    public List<DetailedCourseDTO> toDetailedDTO(List<CourseEntity> courseList) {
        return courseList.stream()
                .map(this::toDetailedDTO)
                .toList();
    }

    public List<CourseEntity> toEntity(List<BasicCourseDTO> basicCourseDTOList) {
        return basicCourseDTOList.stream()
                .map(this::toEntity)
                .toList();
    }

    public void updateEntityFromDetailed(DetailedCourseDTO dto, CourseEntity entity) {
        if (dto == null) return;
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setLevel(dto.level());
        entity.setRoom(dto.room());
        entity.setTotalCourseTime(dto.totalCourseTime());
        entity.setCompletedCourseTime(dto.completedCourseTime());
    }
}
