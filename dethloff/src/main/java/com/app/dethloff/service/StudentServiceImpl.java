package com.app.dethloff.service;

import com.app.dethloff.DAO.StudentDAO;
import com.app.dethloff.DTO.StudentDTO;
import com.app.dethloff.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void create(StudentDTO studentDTO) {
        Student tempStudent = Student.builder()
                .name(studentDTO.getName())
                .surname(studentDTO.getSurname())
                .build();
        System.out.println("student service: " + tempStudent.getName() + " " + tempStudent.getSurname());
        studentDAO.save(tempStudent);
    }
}
