package com.app.dethloff.service;

import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.DTO.BasicTeacherDTO;
import com.app.dethloff.model.DTO.DetailedTeacherDTO;
import com.app.dethloff.model.DTO.mappers.TeacherMapper;
import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import com.app.dethloff.model.TeacherEntity;
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
    public DetailedTeacherDTO get(String id) {
        TeacherEntity teacher = teacherDAO.findById(id)
                .orElseThrow(TeacherNotFoundException::new);
        return teacherMapper.entityToDetailed(teacher);
    }

    @Override
    public List<BasicTeacherDTO> getAll() {
        List<TeacherEntity> teachers = teacherDAO.findAll()
                .orElseThrow(() -> new TeacherNotFoundException("No teachers found"));
        return teacherMapper.toBasicDTO(teachers);
    }

    @Override
    @Transactional
    public DetailedTeacherDTO create(DetailedTeacherDTO teacherDTO) {
        TeacherEntity teacher = teacherMapper.detailedToEntity(teacherDTO);

        teacher.setIsActive(false);
        teacherDAO.save(teacher);
        return teacherMapper.entityToDetailed(teacher);
    }

    @Override
    @Transactional
    public DetailedTeacherDTO update(DetailedTeacherDTO teacherDTO) {
        TeacherEntity existingTeacher = teacherDAO.findById(teacherDTO.id())
                .orElseThrow(TeacherNotFoundException::new);
        teacherMapper.updateEntityFromDetailed(teacherDTO, existingTeacher);
        TeacherEntity updatedTeacher = teacherDAO.update(existingTeacher);
        return teacherMapper.entityToDetailed(updatedTeacher);
    }

    @Override
    @Transactional
    public void delete(String id) {
        teacherDAO.findById(id)
                .orElseThrow(TeacherNotFoundException::new);
        teacherDAO.delete(id);
    }
}
