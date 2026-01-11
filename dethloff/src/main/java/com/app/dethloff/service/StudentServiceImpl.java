package com.app.dethloff.service;

import com.app.dethloff.DAO.StudentDAO;
import com.app.dethloff.DTO.StudentDTO;
import com.app.dethloff.DTO.mappers.StudentMapper;
import com.app.dethloff.error.StudentNotFoundException;
import com.app.dethloff.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    @Transactional
    public StudentDTO create(StudentDTO studentDTO) {
        Student student = StudentMapper.toStudent(studentDTO);

        Student savedStudent = studentDAO.save(student);
        return StudentMapper.toDTO(savedStudent);
    }

    @Override
    public StudentDTO get(String id) {
        Student student = studentDAO.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + id));

        return StudentMapper.toDTO(student);
    }

    @Override
    public List<StudentDTO> getAll() {
        List<Student> list = studentDAO.findAll()
                .orElseThrow(() -> new StudentNotFoundException("No students found"));

        return StudentMapper.toDTO(list);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Student toBeDeleted = studentDAO.findById(id)
                        .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + id));
        studentDAO.remove(toBeDeleted);
    }

    @Override
    @Transactional
    public StudentDTO update(StudentDTO studentDTO) {
        Student student = StudentMapper.toStudent(studentDTO);
        studentDAO.findById(student.getId())
                        .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + student.getId()));
        Student updatedStudent = studentDAO.update(student);

        return StudentMapper.toDTO(updatedStudent);
    }
}
