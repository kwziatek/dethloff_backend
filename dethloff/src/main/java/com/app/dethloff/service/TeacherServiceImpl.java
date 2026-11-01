package com.app.dethloff.service;

import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import com.app.dethloff.model.TeacherEntity;
import com.app.dethloff.repository.TeacherRepository;
import com.app.dethloff.rest.DTO.TeacherRequestDTO;
import com.app.dethloff.rest.DTO.TeacherResponseDTO;
import com.app.dethloff.rest.mappers.TeacherMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    final TeacherRepository teacherRepo;
    final TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepo, TeacherMapper teacherMapper) {
        this.teacherRepo = teacherRepo;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherResponseDTO get(String id) {
        TeacherEntity teacher = teacherRepo.findById(id)
                .orElseThrow(TeacherNotFoundException::new);
        return teacherMapper.entityToResponse(teacher);
    }

    @Override
    public List<TeacherResponseDTO> getAll() {
        List<TeacherEntity> teachers = teacherRepo.findAll();
        return teachers.stream()
                .map(teacherMapper::entityToResponse)
                .toList();
    }

    @Override
    @Transactional
    public TeacherResponseDTO create(TeacherRequestDTO teacherDTO) {
        TeacherEntity teacher = teacherMapper.requestToEntity(teacherDTO);

        teacher.setIsActive(false);
        teacherRepo.save(teacher);

        return teacherMapper.entityToResponse(teacher);
    }

    @Override
    @Transactional
    public TeacherResponseDTO update(TeacherRequestDTO teacherDTO) {
        TeacherEntity existingTeacher = teacherRepo.findById(teacherDTO.id())
                .orElseThrow(TeacherNotFoundException::new);
        teacherMapper.updateEntityFromRequest(teacherDTO, existingTeacher);
        return teacherMapper.entityToResponse(teacherRepo.save(existingTeacher));
    }

    @Override
    @Transactional
    public void delete(String id) {
        TeacherEntity teacher= teacherRepo.findById(id)
                .orElseThrow(TeacherNotFoundException::new);
        teacherRepo.delete(teacher);
    }
}
