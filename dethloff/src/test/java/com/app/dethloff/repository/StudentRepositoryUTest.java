package com.app.dethloff.repository;

import com.app.dethloff.model.StudentEntity;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentRepositoryUTest {

    private StudentEntity sampleStudent;

    @Inject
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        UUID studentId = UUID.randomUUID();

        sampleStudent = StudentEntity.builder()
                .id(studentId.toString())
                .name("Karol")
                .surname("Piątkowski")
                .courses(new ArrayList<>())
                .build();
    }

    @Test
    void shouldFindStudentById() {
        when(studentRepository.findById(sampleStudent.getId())).thenReturn(Optional.ofNullable(sampleStudent));

        Optional<StudentEntity> entity = studentRepository.findById(sampleStudent.getId());

        assertNotNull(entity);
        assertTrue(entity.isPresent());
        assertEquals(sampleStudent.getId(), entity.get().getId());
    }
}
