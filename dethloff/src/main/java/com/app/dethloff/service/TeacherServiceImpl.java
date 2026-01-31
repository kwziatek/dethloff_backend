package com.app.dethloff.service;

import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.DTO.TeacherRequestDTO;
import com.app.dethloff.model.DTO.TeacherResponseDTO;
import com.app.dethloff.model.DTO.mappers.TeacherMapper;
import com.app.dethloff.rest.error.TeacherNotFoundException;
import com.app.dethloff.model.Teacher;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    TeacherDAO teacherDAO;
    TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO, TeacherMapper teacherMapper) {
        this.teacherDAO = teacherDAO;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherResponseDTO get(String id) {
        Teacher teacher = teacherDAO.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("No teacher with such id - " + id));
        return teacherMapper.toDTO(teacher);
    }

    @Override
    public List<TeacherResponseDTO> getAll() {
        List<Teacher> teachers = teacherDAO.findAll()
                .orElseThrow(() -> new TeacherNotFoundException("No teachers found"));
        return teacherMapper.toDTO(teachers);
    }

    @Override
    @Transactional
    public TeacherResponseDTO create(TeacherRequestDTO teacherDTO) {
        Teacher teacher = teacherDAO.save(teacherMapper.toTeacher(teacherDTO));
        return teacherMapper.toDTO(teacher);
    }

    @Override
    @Transactional
    public TeacherResponseDTO update(TeacherRequestDTO teacherDTO) {
        teacherDAO.findById(teacherDTO.id())
                .orElseThrow(() -> new TeacherNotFoundException("No teacher with such id - " + teacherDTO.id()));
        Teacher teacher = teacherDAO.update(teacherMapper.toTeacher(teacherDTO));
        return teacherMapper.toDTO(teacher);
    }

    @Override
    @Transactional
    public void delete(String id) {
        teacherDAO.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("No teachers found"));
        teacherDAO.delete(id);
    }
}
