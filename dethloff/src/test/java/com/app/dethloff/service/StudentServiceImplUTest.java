package com.app.dethloff.service;

import com.app.dethloff.exceptions.model.StudentNotFoundException;
import com.app.dethloff.model.StudentEntity;
import com.app.dethloff.model.pesel.Pesel;
import com.app.dethloff.repository.StudentRepository;
import com.app.dethloff.rest.DTO.StudentRequestDTO;
import com.app.dethloff.rest.DTO.StudentResponseDTO;
import com.app.dethloff.rest.mappers.StudentMapper;
import com.app.dethloff.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplUTest {

  @Mock StudentRepository studentRepository;

  @Mock StudentMapper studentMapper;

  @InjectMocks
  StudentServiceImpl studentService;

  @Captor ArgumentCaptor<StudentEntity> entityCaptor;

  @Test
  void getReturnsTheStudentCorrectly() {
    // given
    StudentEntity entity = buildStudentEntity1();
    String id = entity.getId();
    StudentResponseDTO expectedResponse = buildStudentResponseDTO1();
    when(studentRepository.findById(id)).thenReturn(Optional.of(entity));
    when(studentMapper.entityToResponse(entity)).thenReturn(expectedResponse);

    // when
    StudentResponseDTO actualResponse = studentService.get(id);

    // then
    // 1. Verify that findById() was called
    // 2. Verify that the expected response matches the actual response
    verify(studentRepository).findById(id);
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void getThrowsExceptionWhenTeacherNotFound() {
    // given
    String id = UUID.randomUUID().toString();
    when(studentRepository.findById(id)).thenReturn(Optional.empty());

    // when and then
    assertThrows(StudentNotFoundException.class, () -> studentService.get(id));

    // verify
    // 1. Check if findById() was called
    // 2. Check if none of studentMapper methods were called
    verify(studentRepository).findById(id);
    verifyNoInteractions(studentMapper);
  }

  @Test
  void getAllReturnsAllStudents() {
    // given
    List<StudentEntity> entities = List.of(buildStudentEntity1(), buildStudentEntity1());
    List<StudentResponseDTO> expectedResponse =
        List.of(buildStudentResponseDTO1(), buildStudentResponseDTO2());
    when(studentRepository.findAll()).thenReturn(entities);
    when(studentMapper.entityToResponse(entities.getFirst()))
        .thenReturn(expectedResponse.getFirst());
    when(studentMapper.entityToResponse(entities.get(1))).thenReturn(expectedResponse.get(1));

    // when
    List<StudentResponseDTO> actualResponse = studentService.getAll();

    // then
    // 1. Verify that findAll() was called
    // 2. Check if the expected response matches the actual response
    verify(studentRepository).findAll();
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void getAllReturnsEmptyListWhenDatabaseIsEmpty() {
    // given
    when(studentRepository.findAll()).thenReturn(List.of());

    // when
    List<StudentResponseDTO> responseDTOS = studentService.getAll();

    // then
    // 1. Verify that findAll() was called
    // 2. Verify that none of the studentMapper methods were called
    // 3. Check if the response is an empty list
    verify(studentRepository).findAll();
    verifyNoInteractions(studentMapper);
    assertThat(responseDTOS).isEmpty();
  }

  @Test
  void createSavesStudentAsInactiveAndReturnsResponse() {
    // given
    StudentRequestDTO request = buildStudentRequestDTO2();
    StudentResponseDTO expectedResponse = buildStudentResponseDTO2();

    StudentEntity entity = buildStudentEntity2();
    entity.setIsActive(true);

    when(studentMapper.requestToEntity(request)).thenReturn(entity);
    when(studentMapper.entityToResponse(entity)).thenReturn(expectedResponse);

    // when
    StudentResponseDTO actualResponse = studentService.create(request);

    // then
    // 1. Verify that save() was called and capture the entity
    verify(studentRepository).save(entityCaptor.capture());
    StudentEntity savedEntity = entityCaptor.getValue();

    // 2. Assert business logic was applied before saving
    assertThat(savedEntity.getIsActive()).isFalse();
    // 3. Check if the expected response matches the actual one
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void deleteThrowsExceptionWhenStudentNotFound() {
    // given
    String id = UUID.randomUUID().toString();
    when(studentRepository.findById(id)).thenReturn(Optional.empty());

    // when and then
    assertThrows(StudentNotFoundException.class, () -> studentService.delete(id));

    // 1. assert delete() from repo was NEVER called
    verify(studentRepository, never()).delete(any());
  }

  @Test
  void deleteCorrectlyRemovesStudentFromDB() {
    // given
    String id = UUID.randomUUID().toString();
    StudentEntity entity = buildStudentEntity1();
    when(studentRepository.findById(id)).thenReturn(Optional.of(entity));

    // when
    studentService.delete(id);

    // then
    // 1. verify that delete() was called on entity
    verify(studentRepository).delete(entity);
  }

  @Test
  void updateThrowsExceptionWhenStudentNotFound() {
    // given
    StudentRequestDTO requestDTO = buildStudentRequestDTO1();
    String id = requestDTO.id();
    when(studentRepository.findById(id)).thenReturn(Optional.empty());

    // when and then
    assertThrows(StudentNotFoundException.class, () -> studentService.update(requestDTO));

    // verify
    // 1. Verify that save was NEVER called
    // 2. Verify that updateEntityFromRequest (mapping) was NEVER called
    verify(studentRepository, never()).save(any());
    verify(studentMapper, never()).updateEntityFromRequest(any(), any());
  }

  @Test
  void updateCorrectlyUpdatesStudent() {
    // given
    StudentRequestDTO requestDTO = buildStudentRequestDTO2();
    StudentEntity entity = buildStudentEntity2();
    StudentResponseDTO expectedResponse = buildStudentResponseDTO2();
    String id = requestDTO.id();
    when(studentRepository.findById(id)).thenReturn(Optional.of(entity));
    when(studentRepository.save(entity)).thenReturn(entity);
    when(studentMapper.entityToResponse(entity)).thenReturn(expectedResponse);

    // when
    StudentResponseDTO responseDTO = studentService.update(requestDTO);

    // then
    // 1. Verify that mapper was called with the target entity and that the target entity has been
    // saved
    // 2. Check if the expected response and actual response are the same
    verify(studentMapper).updateEntityFromRequest(requestDTO, entity);
    verify(studentRepository).save(entity);
    assertThat(responseDTO).isEqualTo(expectedResponse);
  }

  private StudentEntity buildStudentEntity1() {
    return StudentEntity.builder()
        .id("55948636-0dc1-403e-8d1e-a93637301c3a")
        .name("Krystian")
        .surname("Wilk")
        .pesel(new Pesel("72051384833"))
        .birthDate(LocalDate.of(1972, 5, 13))
        .placeOfBirth("Jastrzebia Gora")
        .build();
  }

  private StudentEntity buildStudentEntity2() {
    return StudentEntity.builder()
        .id("719ba0c9-ce0e-4b51-8c04-6f1a3a71de94")
        .name("Maria")
        .surname("Japonka")
        .pesel(new Pesel("03260176564"))
        .birthDate(LocalDate.of(2003, 6, 1))
        .placeOfBirth("Jastrzebia Gora")
        .build();
  }

  private StudentRequestDTO buildStudentRequestDTO1() {
    return StudentRequestDTO.builder()
        .id("55948636-0dc1-403e-8d1e-a93637301c3a")
        .name("Krystian")
        .surname("Wilk")
        .pesel("72051384833")
        .birthDate(LocalDate.of(1972, 5, 13))
        .placeOfBirth("Jastrzebia Gora")
        .build();
  }

  private StudentRequestDTO buildStudentRequestDTO2() {
    return StudentRequestDTO.builder()
        .id("719ba0c9-ce0e-4b51-8c04-6f1a3a71de94")
        .name("Maria")
        .surname("Japonka")
        .pesel("03260176564")
        .birthDate(LocalDate.of(2003, 6, 1))
        .placeOfBirth("Jastrzebia Gora")
        .build();
  }

  private StudentResponseDTO buildStudentResponseDTO1() {
    return StudentResponseDTO.builder()
        .id("55948636-0dc1-403e-8d1e-a93637301c3a")
        .name("Krystian")
        .surname("Wilk")
        .pesel("72051384833")
        .birthDate(LocalDate.of(1972, 5, 13))
        .placeOfBirth("Jastrzebia Gora")
        .build();
  }

  private StudentResponseDTO buildStudentResponseDTO2() {
    return StudentResponseDTO.builder()
        .id("719ba0c9-ce0e-4b51-8c04-6f1a3a71de94")
        .name("Maria")
        .surname("Japonka")
        .pesel("03260176564")
        .birthDate(LocalDate.of(2003, 6, 1))
        .placeOfBirth("Jastrzebia Gora")
        .build();
  }
}
