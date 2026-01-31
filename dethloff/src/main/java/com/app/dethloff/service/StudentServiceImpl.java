package com.app.dethloff.service;

import com.app.dethloff.dao.StudentDAO;
import com.app.dethloff.model.DTO.StudentRequestDTO;
import com.app.dethloff.model.DTO.StudentResponseDTO;
import com.app.dethloff.model.DTO.mappers.StudentMapper;
import com.app.dethloff.rest.error.StudentNotFoundException;
import com.app.dethloff.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentDAO studentDAO;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO, StudentMapper studentMapper) {
        this.studentDAO = studentDAO;
        this.studentMapper = studentMapper;
    }

    @Override
    @Transactional
    public StudentResponseDTO create(StudentRequestDTO studentDTO) {
        Student student = studentMapper.toStudent(studentDTO);

        Student savedStudent = studentDAO.save(student);
        return studentMapper.toDTO(savedStudent);
    }

    @Override
    public StudentResponseDTO get(String id) {
        Student student = studentDAO.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + id));

        return studentMapper.toDTO(student);
    }

    @Override
    public List<StudentResponseDTO> getAll() {
        List<Student> list = studentDAO.findAll()
                .orElseThrow(() -> new StudentNotFoundException("No students found"));

        return studentMapper.toDTO(list);
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
    public StudentResponseDTO update(StudentRequestDTO studentDTO) {
        Student student = studentMapper.toStudent(studentDTO);
        studentDAO.findById(student.getId())
                        .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + student.getId()));
        Student updatedStudent = studentDAO.update(student);

        return studentMapper.toDTO(updatedStudent);
    }
}
