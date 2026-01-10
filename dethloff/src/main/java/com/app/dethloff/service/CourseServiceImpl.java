package com.app.dethloff.service;

import com.app.dethloff.DAO.CourseDAO;
import com.app.dethloff.DTO.CourseDTO;
import com.app.dethloff.DTO.mappers.CourseMapper;
import com.app.dethloff.error.CourseNotFoundException;
import com.app.dethloff.model.Course;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseServiceImpl implements CourseService{

    CourseDAO courseDAO;

    @Autowired
    public CourseServiceImpl(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    @Transactional
    public void create(CourseDTO courseDTO) {
        Course course = CourseMapper.toCourse(courseDTO);
        courseDAO.save(course);
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
    public void update(CourseDTO courseDTO) {
        Course course = CourseMapper.toCourse(courseDTO);
        courseDAO.findById(course.getId())
                        .orElseThrow(() -> new CourseNotFoundException("No course with such id" + course.getId()));
        courseDAO.update(course);
    }
}
