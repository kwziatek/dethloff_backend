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
import com.app.dethloff.model.DTO.BasicCourseDTO;
import com.app.dethloff.model.DTO.DetailedCourseDTO;
import com.app.dethloff.model.DTO.SetOfCoursesDTO;
import com.app.dethloff.model.DTO.mappers.CourseMapper;
import com.app.dethloff.model.DTO.mappers.SetOfCoursesMapper;
import com.app.dethloff.model.SetOfCoursesEntity;
import com.app.dethloff.model.StudentEntity;
import jakarta.persistence.Basic;
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
    public DetailedCourseDTO get(String id) {
        CourseEntity course = courseDAO.findById(id)
                .orElseThrow(CourseNotFoundException::new);
        return courseMapper.toDetailedDTO(course);
    }

    @Override
    public List<DetailedCourseDTO> getAll() {
        return courseMapper.toDetailedDTO(courseDAO.findAll());
    }

    @Override
    public List<DetailedCourseDTO> getAllFromParticularSet(String setOfCoursesId) {
        List<CourseEntity> courses = courseDAO.findAllBySetId(setOfCoursesId)
                .orElseThrow(() -> new CourseNotFoundException("No course belongs to set of courses with such id - " + setOfCoursesId));

        return courseMapper.toDetailedDTO(courses);
    }

    @Override
    public List<SetOfCoursesDTO> getAllSetsOfCourses() {
        List<SetOfCoursesEntity> list = setOfCoursesDAO.findAll()
                .orElseThrow(SetOfCoursesNotFoundException::new);

        return setOfCoursesMapper.toDTO(list);
    }

    @Override
    @Transactional
    public DetailedCourseDTO create(BasicCourseDTO basicCourseDTO) {

        Optional.ofNullable(basicCourseDTO.teacherId())
                .filter(teacherDAO::existsById)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found or ID is null"));

        Optional.ofNullable(basicCourseDTO.setOfCoursesId())
                .filter(setOfCoursesDAO::existsById)
                .orElseThrow(() -> new SetOfCoursesNotFoundException("Set of courses not found or ID is null"));

        CourseEntity course = courseMapper.toEntity(basicCourseDTO);
        courseDAO.save(course);
        return courseMapper.toDetailedDTO(course);
    }

    @Transactional
    @Override
    public DetailedCourseDTO update(BasicCourseDTO basicCourseDTO) {
        CourseEntity existingCourse = courseDAO.findById(basicCourseDTO.id())
                .orElseThrow(CourseNotFoundException::new);

        courseMapper.updateEntityFromBasic(basicCourseDTO, existingCourse);

        CourseEntity updatedCourse = courseDAO.update(existingCourse);
        return courseMapper.toDetailedDTO(updatedCourse);
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
