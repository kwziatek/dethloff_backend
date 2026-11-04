package com.app.dethloff.service;

import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import com.app.dethloff.model.TeacherEntity;
import com.app.dethloff.model.pesel.Pesel;
import com.app.dethloff.repository.TeacherRepository;
import com.app.dethloff.rest.DTO.TeacherRequestDTO;
import com.app.dethloff.rest.DTO.TeacherResponseDTO;
import com.app.dethloff.rest.mappers.TeacherMapper;
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
public class TeacherServiceImplUTest {

  @Mock TeacherMapper teacherMapper;

  @Mock TeacherRepository teacherRepository;

  @InjectMocks TeacherServiceImpl teacherService;

  @Captor ArgumentCaptor<TeacherEntity> entityCaptor;

  @Test
  void getReturnsCorrectTeacher() {
    // given
    TeacherEntity entity = buildTeacherEntity1();
    String id = entity.getId();
    TeacherResponseDTO expectedResponse = buildTeacherResponseDTO1();

    when(teacherRepository.findById(id)).thenReturn(Optional.of(entity));
    when(teacherMapper.entityToResponse(entity)).thenReturn(expectedResponse);

    // when
    TeacherResponseDTO response = teacherService.get(id);

    // then
    verify(teacherRepository).findById(id);
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  void getThrowsExceptionWhenTeacherNotFound() {
    // given
    String id = UUID.randomUUID().toString();

    when(teacherRepository.findById(id)).thenReturn(Optional.empty());

    // when and then
    assertThrows(TeacherNotFoundException.class, () -> teacherService.get(id));

    // 1. Verify if findById was called
    verify(teacherRepository).findById(id);
  }

  @Test
  void getAllReturnsAllTeachers() {
    // given
    List<TeacherEntity> entities = List.of(buildTeacherEntity1(), buildTeacherEntity2());

    when(teacherRepository.findAll()).thenReturn(entities);
    when(teacherMapper.entityToResponse(entities.getFirst()))
        .thenReturn(buildTeacherResponseDTO1());
    when(teacherMapper.entityToResponse(entities.get(1))).thenReturn(buildTeacherResponseDTO2());

    // when
    List<TeacherResponseDTO> responses = teacherService.getAll();

    // then
    verify(teacherRepository).findAll();

    assertThat(responses).containsExactly(buildTeacherResponseDTO1(), buildTeacherResponseDTO2());
  }

  @Test
  void getAllReturnEmptyListWhenDatabaseIsEmpty() {
    // given
    when(teacherRepository.findAll()).thenReturn(List.of());

    // when
    List<TeacherResponseDTO> response = teacherService.getAll();

    // then
    // 1. Check if findAll() was called
    // 2. Check if none of teacherMapper methods were called (no mapping due to the empty list)
    // 3. Check if the response list is empty
    verify(teacherRepository).findAll();
    verifyNoInteractions(teacherMapper);
    assertThat(response).isEmpty();
  }

  @Test
  void createSavesTeacherAsInactiveAndReturnsResponse() {
    // given
    TeacherRequestDTO request = buildTeacherRequestDTO1();
    TeacherResponseDTO expectedResponse = buildTeacherResponseDTO1();

    // Create an entity. We can set isActive to true initially to prove the method actually changes
    // it to false.
    TeacherEntity mappedEntity = buildTeacherEntity1();
    mappedEntity.setIsActive(true);

    when(teacherMapper.requestToEntity(request)).thenReturn(mappedEntity);
    when(teacherMapper.entityToResponse(mappedEntity)).thenReturn(expectedResponse);

    // when
    TeacherResponseDTO response = teacherService.create(request);

    // then
    // 1. Verify save was called and capture the exact entity passed to the repository
    verify(teacherRepository).save(entityCaptor.capture());
    TeacherEntity savedEntity = entityCaptor.getValue();

    // 2. Assert the business logic was applied BEFORE saving
    assertThat(savedEntity.getIsActive()).isFalse();

    // 3. Assert the service returns the expected DTO from the mapper
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  void deleteCorrectlyRemovesTeacherFromDB() {
    // given
    TeacherEntity entity = buildTeacherEntity2();
    String id = entity.getId();

    when(teacherRepository.findById(id)).thenReturn(Optional.of(entity));

    // when
    teacherService.delete(id);

    // then
    // 1. Checking if findById and delete methods were called
    verify(teacherRepository).findById(id);
    verify(teacherRepository).delete(entity);
  }

  @Test
  void deleteThrowsExceptionWhenTeacherNotFound() {
    // given
    String id = UUID.randomUUID().toString();

    when(teacherRepository.findById(id)).thenReturn(Optional.empty());

    // when and then
    assertThrows(TeacherNotFoundException.class, () -> teacherService.delete(id));

    // 1. Verify that delete was NEVER called
    verify(teacherRepository, never()).delete(any());
  }

  @Test
  void updateThrowsExceptionWhenTeacherNotFound() {
    // given
    TeacherRequestDTO requestDTO = buildTeacherRequestDTO2();
    String id = requestDTO.id();
    when(teacherRepository.findById(id)).thenReturn(Optional.empty());

    // when and then
    assertThrows(TeacherNotFoundException.class, () -> teacherService.update(requestDTO));

    // verify
    // 1. Verify that save was NEVER called
    // 2. Verify that updateEntityFromRequest (mapping) was NEVER called
    verify(teacherRepository, never()).save(any());
    verify(teacherMapper, never()).updateEntityFromRequest(any(), any());
  }

  @Test
  void updateCorrectlyUpdatesTeacher() {
    // given
    TeacherRequestDTO requestDTO = buildTeacherRequestDTO2();
    TeacherEntity entity = buildTeacherEntity2();
    TeacherResponseDTO expectedResponse = buildTeacherResponseDTO2();
    String id = requestDTO.id();
    when(teacherRepository.findById(id)).thenReturn(Optional.of(entity));
    when(teacherRepository.save(entity)).thenReturn(entity);
    when(teacherMapper.entityToResponse(entity)).thenReturn(expectedResponse);

    // when
    TeacherResponseDTO responseDTO = teacherService.update(requestDTO);

    // then
    // 1. Verify that mapper was called with the target entity and that the target entity has been
    // saved
    // 2. Check if the expected response and actual response are the same
    verify(teacherMapper).updateEntityFromRequest(requestDTO, entity);
    verify(teacherRepository).save(entity);
    assertThat(responseDTO).isEqualTo(expectedResponse);
  }

  private TeacherRequestDTO buildTeacherRequestDTO1() {
    return TeacherRequestDTO.builder()
        .id("dd7f5674-3445-4cae-b297-fbcef545aaa6")
        .name("Jan")
        .surname("Kowalski")
        .pesel("75071559492")
        .birthDate(LocalDate.of(1975, 7, 15))
        .placeOfBirth("Tarnobrzeg")
        .isActive(true)
        .build();
  }

  private TeacherRequestDTO buildTeacherRequestDTO2() {
    return TeacherRequestDTO.builder()
        .id("9f0f4c3d-5628-43f5-af0e-078d9811582b")
        .name("Janina")
        .surname("Brytkiewicz")
        .pesel("63040393185")
        .birthDate(LocalDate.of(1963, 4, 3))
        .placeOfBirth("Lublin")
        .isActive(true)
        .build();
  }

  private TeacherResponseDTO buildTeacherResponseDTO1() {
    return TeacherResponseDTO.builder()
        .id("dd7f5674-3445-4cae-b297-fbcef545aaa6")
        .name("Jan")
        .surname("Kowalski")
        .pesel("75071559492")
        .birthDate(LocalDate.of(1975, 7, 15))
        .placeOfBirth("Tarnobrzeg")
        .isActive(true)
        .build();
  }

  private TeacherResponseDTO buildTeacherResponseDTO2() {
    return TeacherResponseDTO.builder()
        .id("9f0f4c3d-5628-43f5-af0e-078d9811582b")
        .name("Janina")
        .surname("Brytkiewicz")
        .pesel("63040393185")
        .birthDate(LocalDate.of(1963, 4, 3))
        .placeOfBirth("Lublin")
        .isActive(true)
        .build();
  }

  private TeacherEntity buildTeacherEntity1() {
    return TeacherEntity.builder()
        .id("dd7f5674-3445-4cae-b297-fbcef545aaa6")
        .name("Jan")
        .surname("Kowalski")
        .pesel(new Pesel("75071559492"))
        .birthDate(LocalDate.of(1975, 7, 15))
        .placeOfBirth("Tarnobrzeg")
        .isActive(true)
        .build();
  }

  private TeacherEntity buildTeacherEntity2() {
    return TeacherEntity.builder()
        .id("9f0f4c3d-5628-43f5-af0e-078d9811582b")
        .name("Janina")
        .surname("Brytkiewicz")
        .pesel(new Pesel("63040393185"))
        .birthDate(LocalDate.of(1963, 4, 3))
        .placeOfBirth("Lublin")
        .isActive(true)
        .build();
  }
}
