package com.app.dethloff.service;

import com.app.dethloff.exceptions.model.StudentNotFoundException;
import com.app.dethloff.model.StudentEntity;
import com.app.dethloff.repository.StudentRepository;
import com.app.dethloff.rest.DTO.StudentRequestDTO;
import com.app.dethloff.rest.DTO.StudentResponseDTO;
import com.app.dethloff.rest.mappers.StudentMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepo;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepo, StudentMapper studentMapper) {
        this.studentRepo = studentRepo;
        this.studentMapper = studentMapper;
    }

    @Override
    @Transactional
    public StudentResponseDTO create(StudentRequestDTO studentRequestDTO) {
        StudentEntity student = studentMapper.requestToEntity(studentRequestDTO);

        student.setIsActive(false);
        studentRepo.save(student);

        return studentMapper.entityToResponse(student);
    }

    @Override
    public StudentResponseDTO get(String id) {
        StudentEntity student = studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + id));

        return studentMapper.entityToResponse(student);
    }

    @Override
    public List<StudentResponseDTO> getAll() {
        List<StudentEntity> list = studentRepo.findAll();

        return list.stream()
                .map(studentMapper::entityToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(String id) {
        StudentEntity toBeDeleted = studentRepo.findById(id)
                        .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + id));
        studentRepo.delete(toBeDeleted);
    }

    @Override
    @Transactional
    public StudentResponseDTO update(StudentRequestDTO studentRequestDTO) {
        StudentEntity existingStudent = studentRepo.findById(studentRequestDTO.id())
                .orElseThrow(() -> new StudentNotFoundException("No student with such id - " + studentRequestDTO.id()));

        studentMapper.updateEntityFromRequest(studentRequestDTO, existingStudent);

        StudentEntity savedStudent = studentRepo.save(existingStudent);
        return studentMapper.entityToResponse(savedStudent);
    }
}
