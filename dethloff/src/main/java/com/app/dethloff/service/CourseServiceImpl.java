package com.app.dethloff.service;

import com.app.dethloff.dao.CourseDAO;
import com.app.dethloff.dao.StudentDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.DTO.BasicCourseDTO;
import com.app.dethloff.model.DTO.DetailedCourseDTO;
import com.app.dethloff.model.DTO.mappers.CourseMapper;
import com.app.dethloff.exceptions.model.CourseNotFoundException;
import com.app.dethloff.exceptions.model.StudentNotFoundException;
import com.app.dethloff.model.CourseEntity;
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
    CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseDAO courseDAO, StudentDAO studentDAO, TeacherDAO teacherDAO, CourseMapper courseMapper) {
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
        this.teacherDAO = teacherDAO;
        this.courseMapper = courseMapper;
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
                .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + id));
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
                .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + id));
        courseDAO.remove(course);
    }

    @Transactional
    @Override
    public DetailedCourseDTO update(BasicCourseDTO basicCourseDTO) {
        CourseEntity course = courseMapper.basicToEntity(basicCourseDTO);
        courseDAO.findById(course.getId())
                        .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + course.getId()));
        CourseEntity updatedCourse = courseDAO.update(course);
        return courseMapper.toDetailedDTO(updatedCourse);
    }

    @Override
    @Transactional
    public void enrollStudent(String courseId, String studentId) {
        CourseEntity course = courseDAO.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + courseId));
        StudentEntity student = studentDAO.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + studentId));
        course.addStudent(student);
        student.setIsActive(true);
    }

    @Override
    @Transactional
    public void unenrollStudent(String courseId, String studentId) {
        CourseEntity course = courseDAO.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + courseId));
        StudentEntity student = studentDAO.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + studentId));
        course.removeStudent(student);
        if(student.getCourses().isEmpty()) {
            student.setIsActive(false);
        }
    }
}
