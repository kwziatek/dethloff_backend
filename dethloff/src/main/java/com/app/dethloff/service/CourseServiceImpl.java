package com.app.dethloff.service;

import com.app.dethloff.dao.*;
import com.app.dethloff.exceptions.model.SetOfCoursesNotFoundException;
import com.app.dethloff.model.DTO.BasicCourseDTO;
import com.app.dethloff.model.DTO.DetailedCourseDTO;
import com.app.dethloff.model.DTO.SetOfCoursesDTO;
import com.app.dethloff.model.DTO.mappers.CourseMapper;
import com.app.dethloff.exceptions.model.CourseNotFoundException;
import com.app.dethloff.exceptions.model.StudentNotFoundException;
import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.model.DTO.mappers.SetOfCoursesMapper;
import com.app.dethloff.model.SetOfCoursesEntity;
import com.app.dethloff.model.StudentEntity;
import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseServiceImpl implements CourseService{

    CourseDAO courseDAO;
    StudentDAO studentDAO;
    TeacherDAO teacherDAO;
    SetOfCoursesDAO setOfCoursesDAO;
    CourseMapper courseMapper;
    SetOfCoursesMapper setOfCoursesMapper;

    @Autowired
    public CourseServiceImpl(CourseDAO courseDAO, StudentDAO studentDAO, TeacherDAO teacherDAO, CourseMapper courseMapper, SetOfCoursesDAO setOfCoursesDAO, SetOfCoursesMapper setOfCoursesMapper) {
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
        this.teacherDAO = teacherDAO;
        this.courseMapper = courseMapper;
        this.setOfCoursesDAO = setOfCoursesDAO;
        this.setOfCoursesMapper = setOfCoursesMapper;
    }

    @Override
    @Transactional
    public DetailedCourseDTO create(BasicCourseDTO basicCourseDTO) {
        System.out.println();
        if(basicCourseDTO.teacherId() != null) {
            if(!teacherDAO.existsById(basicCourseDTO.teacherId())) {
                throw new TeacherNotFoundException("No teacher with such id - " + basicCourseDTO.id());
            }
        }

        CourseEntity course = courseMapper.basicToEntity(basicCourseDTO);
        courseDAO.save(course);
        return courseMapper.toDetailedDTO(course);
    }

    @Override
    public DetailedCourseDTO get(String id) {
        CourseEntity course = courseDAO.findById(id)
                .orElseThrow(CourseNotFoundException::new);
        return courseMapper.toDetailedDTO(course);
    }

    @Override
    public List<DetailedCourseDTO> getAll() {
        List<CourseEntity> list = courseDAO.findAll()
                .orElseThrow(() -> new CourseNotFoundException("No course found"));
        return courseMapper.toDetailedDTO(list);
    }

    @Transactional
    @Override
    public void delete(String id) {
        CourseEntity course = courseDAO.findById(id)
                .orElseThrow(CourseNotFoundException::new);
        courseDAO.remove(course);
    }

    @Transactional
    @Override
    public DetailedCourseDTO update(BasicCourseDTO basicCourseDTO) {
        CourseEntity course = courseMapper.basicToEntity(basicCourseDTO);
        courseDAO.findById(course.getId())
                        .orElseThrow(CourseNotFoundException::new);
        CourseEntity updatedCourse = courseDAO.update(course);
        return courseMapper.toDetailedDTO(updatedCourse);
    }

    @Override
    @Transactional
    public void enrollStudent(String courseId, String studentId) {
        CourseEntity course = courseDAO.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);
        StudentEntity student = studentDAO.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);
        course.addStudent(student);
        student.setIsActive(true);
    }

    @Override
    @Transactional
    public void unenrollStudent(String courseId, String studentId) {
        CourseEntity course = courseDAO.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);
        StudentEntity student = studentDAO.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);
        course.removeStudent(student);
        if(student.getCourses().isEmpty()) {
            student.setIsActive(false);
        }
    }

    @Override
    public List<DetailedCourseDTO> getAllFromParticularSet(String setOfCoursesId) {
        List<CourseEntity> courses = courseDAO.findAllBySetId(setOfCoursesId)
                .orElseThrow(() -> new CourseNotFoundException("No course with belongs to set of courses with id - " + setOfCoursesId));

        return courseMapper.toDetailedDTO(courses);
    }

    @Override
    public List<SetOfCoursesDTO> getAllSetsOfCourses() {
        List<SetOfCoursesEntity> list = setOfCoursesDAO.findAll()
                .orElseThrow(SetOfCoursesNotFoundException::new);

        return setOfCoursesMapper.toDTO(list);
    }
}
