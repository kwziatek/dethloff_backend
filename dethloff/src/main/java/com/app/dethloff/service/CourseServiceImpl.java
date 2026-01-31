package com.app.dethloff.service;

import com.app.dethloff.dao.CourseDAO;
import com.app.dethloff.dao.StudentDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.DTO.CourseRequestDTO;
import com.app.dethloff.model.DTO.CourseResponseDTO;
import com.app.dethloff.model.DTO.mappers.CourseMapper;
import com.app.dethloff.rest.error.CourseNotFoundException;
import com.app.dethloff.rest.error.StudentNotFoundException;
import com.app.dethloff.model.Course;
import com.app.dethloff.model.Student;
import com.app.dethloff.rest.error.TeacherNotFoundException;
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
    public CourseResponseDTO create(CourseRequestDTO courseRequestDTO) {
        if(!teacherDAO.existsById(courseRequestDTO.teacherId())) {
            throw new TeacherNotFoundException("No teacher with such id - " + courseRequestDTO.id());
        }

        Course course = courseMapper.toCourse(courseRequestDTO);
        courseDAO.save(course);
        return courseMapper.toDTO(course);
    }

    @Override
    public CourseResponseDTO get(String id) {
        Course course = courseDAO.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + id));
        return courseMapper.toDTO(course);
    }

    @Override
    public List<CourseResponseDTO> getAll() {
        List<Course> list = courseDAO.findAll()
                .orElseThrow(() -> new CourseNotFoundException("No course found"));
        return courseMapper.toDTO(list);
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
    public CourseResponseDTO update(CourseRequestDTO courseRequestDTO) {
        Course course = courseMapper.toCourse(courseRequestDTO);
        courseDAO.findById(course.getId())
                        .orElseThrow(() -> new CourseNotFoundException("No course with such id - " + course.getId()));
        Course updatedCourse = courseDAO.update(course);
        return courseMapper.toDTO(updatedCourse);
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
