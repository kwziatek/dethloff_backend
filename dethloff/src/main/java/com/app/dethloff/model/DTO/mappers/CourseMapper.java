package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.dao.SetOfCoursesDAO;
import com.app.dethloff.dao.StudentDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.*;
import com.app.dethloff.model.DTO.*;
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

    public CourseEntity toEntity(BasicCourseDTO basicCourseDTO) {
        TeacherEntity teacherProxy = null;
        if(basicCourseDTO.teacherId() != null) {
             teacherProxy = teacherDAO.createProxy(basicCourseDTO.teacherId());
        }

        SetOfCoursesEntity setOfCoursesProxy = null;
        if(basicCourseDTO.setOfCoursesId() != null) {
            setOfCoursesProxy = setOfCoursesDAO.createProxy(basicCourseDTO.setOfCoursesId());
        }
        List<StudentEntity> studentProxies = null;
        if(basicCourseDTO.studentIds() != null) {
            studentProxies = basicCourseDTO.studentIds().stream().map(studentDAO::createProxy).toList();
        }

        return CourseEntity.builder()
                .id(basicCourseDTO.id())
                .name(basicCourseDTO.name())
                .level(basicCourseDTO.level())
                .description(basicCourseDTO.description())
                .students(studentProxies)
                .teacher(teacherProxy)
                .setOfCourses(setOfCoursesProxy)
                .build();
    }

    public List<CourseEntity> toEntity(List<BasicCourseDTO> basicCourseDTOList) {
        return basicCourseDTOList.stream()
                .map(this::toEntity)
                .toList();
    }

    public BasicCourseDTO toBasicDTO(CourseEntity course) {
        return BasicCourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .studentIds(course.getStudents().stream().map(PersonAbstractEntity::getId).toList())
                .teacherId(course.getTeacher().getId())
                .setOfCoursesId(course.getSetOfCourses().getId())
                .build();
    }

    public List<BasicCourseDTO> toBasicDTO(List<CourseEntity> courseList) {
        return courseList.stream()
                .map(this::toBasicDTO)
                .toList();
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

    public List<DetailedCourseDTO> toDetailedDTO(List<CourseEntity> courseList) {
        return courseList.stream()
                .map(this::toDetailedDTO)
                .toList();
    }

    public void updateEntityFromBasic(BasicCourseDTO dto, CourseEntity entity) {
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
