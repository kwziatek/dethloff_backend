package com.app.dethloff.service;

import com.app.dethloff.DAO.CourseDAO;
import com.app.dethloff.DAO.StudentDAO;
import com.app.dethloff.DTO.CourseDTO;
import com.app.dethloff.DTO.mappers.CourseMapper;
import com.app.dethloff.error.CourseNotFoundException;
import com.app.dethloff.error.StudentNotFoundException;
import com.app.dethloff.model.Course;
import com.app.dethloff.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseServiceImpl implements CourseService{

    CourseDAO courseDAO;
    StudentDAO studentDAO;

    @Autowired
    public CourseServiceImpl(CourseDAO courseDAO, StudentDAO studentDAO) {
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
    }

    @Override
    @Transactional
    public CourseDTO create(CourseDTO courseDTO) {
        Course course = CourseMapper.toCourse(courseDTO);
        courseDAO.save(course);
        return CourseMapper.toDTO(course);
    }

    @Override
    public CourseDTO get(String id) {
        Course course = courseDAO.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + id));
        return CourseMapper.toDTO(course);
    }

    @Override
    public List<CourseDTO> getAll() {
        List<Course> list = courseDAO.findAll()
                .orElseThrow(() -> new CourseNotFoundException("No course found"));
        return CourseMapper.toDTO(list);
    }

    @Transactional
    @Override
    public void delete(String id) {
        Course course = courseDAO.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + id));
        courseDAO.remove(course);
    }

    @Transactional
    @Override
    public CourseDTO update(CourseDTO courseDTO) {
        Course course = CourseMapper.toCourse(courseDTO);
        courseDAO.findById(course.getId())
                        .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + course.getId()));
        Course updatedCourse = courseDAO.update(course);
        return CourseMapper.toDTO(updatedCourse);
    }

    @Override
    @Transactional
    public void enrollStudent(String courseId, String studentId) {
        Course course = courseDAO.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + courseId));
        Student student = studentDAO.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + studentId));
        course.addStudent(student);
    }

    @Override
    @Transactional
    public void unenrollStudent(String courseId, String studentId) {
        Course course = courseDAO.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + courseId));
        Student student = studentDAO.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + studentId));
        course.removeStudent(student);
    }
}
