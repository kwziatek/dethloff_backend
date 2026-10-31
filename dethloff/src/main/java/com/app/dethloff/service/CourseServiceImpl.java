package com.app.dethloff.service;

import com.app.dethloff.exceptions.model.CourseNotFoundException;
import com.app.dethloff.exceptions.model.SetOfCoursesNotFoundException;
import com.app.dethloff.exceptions.model.StudentNotFoundException;
import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.model.SetOfCoursesEntity;
import com.app.dethloff.model.StudentEntity;
import com.app.dethloff.repository.CourseRepository;
import com.app.dethloff.repository.SetOfCoursesRepository;
import com.app.dethloff.repository.StudentRepository;
import com.app.dethloff.repository.TeacherRepository;
import com.app.dethloff.rest.DTO.CourseRequestDTO;
import com.app.dethloff.rest.DTO.CourseResponseDTO;
import com.app.dethloff.rest.DTO.SetOfCoursesResponseDTO;
import com.app.dethloff.rest.mappers.CourseMapper;
import com.app.dethloff.rest.mappers.SetOfCoursesMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;
    private final TeacherRepository teacherRepo;
    private final SetOfCoursesRepository setOfCoursesRepo;
    private final CourseMapper courseMapper;
    private final SetOfCoursesMapper setOfCoursesMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepo, StudentRepository studentRepo, TeacherRepository teacherRepo, CourseMapper courseMapper, SetOfCoursesRepository setOfCoursesRepo, SetOfCoursesMapper setOfCoursesMapper) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.courseMapper = courseMapper;
        this.setOfCoursesRepo = setOfCoursesRepo;
        this.setOfCoursesMapper = setOfCoursesMapper;
    }

    @Override
    public CourseResponseDTO get(String id) {
        CourseEntity course = courseRepo.findById(id)
                .orElseThrow(CourseNotFoundException::new);
        return courseMapper.toCourseResponseDTO(course);
    }

    @Override
    public List<CourseResponseDTO> getAll() {
        return courseMapper.toCourseResponseDTO(courseRepo.findAll());
    }

    @Override
    public List<CourseResponseDTO> getAllFromParticularSet(String setOfCoursesId) {
        List<CourseEntity> courses = courseRepo.findAllBySetOfCourses_Id(setOfCoursesId);

        return courseMapper.toCourseResponseDTO(courses);
    }

    @Override
    public List<SetOfCoursesResponseDTO> getAllSetsOfCourses() {
        List<SetOfCoursesEntity> list = setOfCoursesRepo.findAll();

        return setOfCoursesMapper.toSetOfCoursesResponseDTO(list);
    }

    @Override
    @Transactional
    public CourseResponseDTO create(CourseRequestDTO courseRequestDTO) {

        Optional.ofNullable(courseRequestDTO.teacherId())
                .filter(teacherRepo::existsById)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found or ID is null"));

        Optional.ofNullable(courseRequestDTO.setOfCoursesId())
                .filter(setOfCoursesRepo::existsById)
                .orElseThrow(() -> new SetOfCoursesNotFoundException("Set of courses not found or ID is null"));

        CourseEntity course = courseMapper.toEntity(courseRequestDTO);
        courseRepo.save(course);
        return courseMapper.toCourseResponseDTO(course);
    }

    @Transactional
    @Override
    public CourseResponseDTO update(CourseRequestDTO courseRequestDTO) {
        CourseEntity existingCourse = courseRepo.findById(courseRequestDTO.id())
                .orElseThrow(CourseNotFoundException::new);

        courseMapper.updateEntityFromRequest(courseRequestDTO, existingCourse);

        CourseEntity updatedCourse = courseRepo.save(existingCourse);
        return courseMapper.toCourseResponseDTO(updatedCourse);
    }

    @Transactional
    @Override
    public void delete(String id) {
        CourseEntity course = courseRepo.findById(id)
                .orElseThrow(CourseNotFoundException::new);
        courseRepo.delete(course);
    }

    @Override
    @Transactional
    public void enrollStudent(String courseId, String studentId) {
        CourseEntity course = courseRepo.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);
        StudentEntity student = studentRepo.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);
        course.addStudent(student);
        student.setIsActive(true);
    }

    @Override
    @Transactional
    public void unenrollStudent(String courseId, String studentId) {
        CourseEntity course = courseRepo.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);
        StudentEntity student = studentRepo.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);
        course.removeStudent(student);
        if(student.getCourses().isEmpty()) {
            student.setIsActive(false);
        }
    }
}
