package com.app.dethloff.service;

import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.rest.DTO.TeacherRequestDTO;
import com.app.dethloff.rest.DTO.TeacherResponseDTO;
import com.app.dethloff.rest.mappers.TeacherMapper;
import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import com.app.dethloff.model.TeacherEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    final TeacherDAO teacherDAO;
    final TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO, TeacherMapper teacherMapper) {
        this.teacherDAO = teacherDAO;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherResponseDTO get(String id) {
        TeacherEntity teacher = teacherDAO.findById(id)
                .orElseThrow(TeacherNotFoundException::new);
        return teacherMapper.toTeacherResponseDTO(teacher);
    }

    @Override
    public List<TeacherResponseDTO> getAll() {
        List<TeacherEntity> teachers = teacherDAO.findAll()
                .orElseThrow(() -> new TeacherNotFoundException("No teachers found"));
        return teacherMapper.toTeacherResponseDTO(teachers);
    }

    @Override
    @Transactional
    public TeacherResponseDTO create(TeacherRequestDTO teacherDTO) {
        TeacherEntity teacher = teacherMapper.toEntity(teacherDTO);

        teacher.setIsActive(false);
        teacherDAO.save(teacher);
        return teacherMapper.toTeacherResponseDTO(teacher);
    }

    @Override
    @Transactional
    public TeacherResponseDTO update(TeacherRequestDTO teacherDTO) {
        TeacherEntity existingTeacher = teacherDAO.findById(teacherDTO.id())
                .orElseThrow(TeacherNotFoundException::new);
        teacherMapper.updateEntityFromRequest(teacherDTO, existingTeacher);
        return teacherMapper.toTeacherResponseDTO(teacherDAO.update(existingTeacher));
    }

    @Override
    @Transactional
    public void delete(String id) {
        teacherDAO.findById(id)
                .orElseThrow(TeacherNotFoundException::new);
        teacherDAO.delete(id);
    }
}
