package com.app.dethloff.service;

import com.app.dethloff.dao.CourseDAO;
import com.app.dethloff.dao.SetOfCoursesDAO;
import com.app.dethloff.dao.StudentDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.exceptions.model.CourseNotFoundException;
import com.app.dethloff.exceptions.model.SetOfCoursesNotFoundException;
import com.app.dethloff.exceptions.model.StudentNotFoundException;
import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.rest.DTO.CourseRequestDTO;
import com.app.dethloff.rest.DTO.CourseResponseDTO;
import com.app.dethloff.rest.DTO.SetOfCoursesResponseDTO;
import com.app.dethloff.rest.mappers.CourseMapper;
import com.app.dethloff.rest.mappers.SetOfCoursesMapper;
import com.app.dethloff.model.SetOfCoursesEntity;
import com.app.dethloff.model.StudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseDAO courseDAO;
    private final StudentDAO studentDAO;
    private final TeacherDAO teacherDAO;
    private final SetOfCoursesDAO setOfCoursesDAO;
    private final CourseMapper courseMapper;
    private final SetOfCoursesMapper setOfCoursesMapper;

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
    public CourseResponseDTO get(String id) {
        CourseEntity course = courseDAO.findById(id)
                .orElseThrow(CourseNotFoundException::new);
        return courseMapper.toCourseResponseDTO(course);
    }

    @Override
    public List<CourseResponseDTO> getAll() {
        return courseMapper.toCourseResponseDTO(courseDAO.findAll());
    }

    @Override
    public List<CourseResponseDTO> getAllFromParticularSet(String setOfCoursesId) {
        List<CourseEntity> courses = courseDAO.findAllBySetId(setOfCoursesId)
                .orElseThrow(() -> new CourseNotFoundException("No course belongs to set of courses with such id - " + setOfCoursesId));

        return courseMapper.toCourseResponseDTO(courses);
    }

    @Override
    public List<SetOfCoursesResponseDTO> getAllSetsOfCourses() {
        List<SetOfCoursesEntity> list = setOfCoursesDAO.findAll()
                .orElseThrow(SetOfCoursesNotFoundException::new);

        return setOfCoursesMapper.toSetOfCoursesResponseDTO(list);
    }

    @Override
    @Transactional
    public CourseResponseDTO create(CourseRequestDTO courseRequestDTO) {

        Optional.ofNullable(courseRequestDTO.teacherId())
                .filter(teacherDAO::existsById)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found or ID is null"));

        Optional.ofNullable(courseRequestDTO.setOfCoursesId())
                .filter(setOfCoursesDAO::existsById)
                .orElseThrow(() -> new SetOfCoursesNotFoundException("Set of courses not found or ID is null"));

        CourseEntity course = courseMapper.toEntity(courseRequestDTO);
        courseDAO.save(course);
        return courseMapper.toCourseResponseDTO(course);
    }

    @Transactional
    @Override
    public CourseResponseDTO update(CourseRequestDTO courseRequestDTO) {
        CourseEntity existingCourse = courseDAO.findById(courseRequestDTO.id())
                .orElseThrow(CourseNotFoundException::new);

        courseMapper.updateEntityFromRequest(courseRequestDTO, existingCourse);

        CourseEntity updatedCourse = courseDAO.update(existingCourse);
        return courseMapper.toCourseResponseDTO(updatedCourse);
    }

    @Transactional
    @Override
    public void delete(String id) {
        CourseEntity course = courseDAO.findById(id)
                .orElseThrow(CourseNotFoundException::new);
        courseDAO.remove(course);
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
}
