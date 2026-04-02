package com.app.dethloff.service;

import com.app.dethloff.dao.StudentDAO;
import com.app.dethloff.model.DTO.DetailedStudentDTO;
import com.app.dethloff.model.DTO.StudentResponseDTO;
import com.app.dethloff.model.DTO.mappers.StudentMapper;
import com.app.dethloff.exceptions.model.StudentNotFoundException;
import com.app.dethloff.model.StudentEntity;
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
    public DetailedStudentDTO create(DetailedStudentDTO detailedStudentDTO) {
        StudentEntity student = studentMapper.detailedToEntity(detailedStudentDTO);

        student.setIsActive(false);
        studentDAO.save(student);

        return studentMapper.entityToDetailed(student);
    }

    @Override
    public DetailedStudentDTO get(String id) {
        StudentEntity student = studentDAO.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + id));

        return studentMapper.entityToDetailed(student);
    }

    @Override
    public List<StudentResponseDTO> getAll() {
        List<StudentEntity> list = studentDAO.findAll()
                .orElseThrow(() -> new StudentNotFoundException("No students found"));

        return studentMapper.toDTO(list);
    }

    @Override
    @Transactional
    public void delete(String id) {
        StudentEntity toBeDeleted = studentDAO.findById(id)
                        .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + id));
        studentDAO.remove(toBeDeleted);
    }

    @Override
    @Transactional
    public DetailedStudentDTO update(DetailedStudentDTO detailedStudentDTO) {
        StudentEntity existingStudent = studentDAO.findById(detailedStudentDTO.id())
                .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + detailedStudentDTO.id()));

        studentMapper.updateEntityFromDetailed(detailedStudentDTO, existingStudent);

        StudentEntity savedStudent = studentDAO.update(existingStudent);
        return studentMapper.entityToDetailed(savedStudent);
    }
}
