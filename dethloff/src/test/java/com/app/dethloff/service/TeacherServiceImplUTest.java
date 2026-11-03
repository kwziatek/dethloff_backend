package com.app.dethloff.service;

import com.app.dethloff.model.TeacherEntity;
import com.app.dethloff.model.pesel.Pesel;
import com.app.dethloff.repository.TeacherRepository;
import com.app.dethloff.rest.DTO.TeacherRequestDTO;
import com.app.dethloff.rest.DTO.TeacherResponseDTO;
import com.app.dethloff.rest.mappers.TeacherMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceImplUTest {

  @Mock TeacherMapper teacherMapper;

  @Mock TeacherRepository teacherRepository;

  @InjectMocks TeacherServiceImpl teacherService;

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

  private TeacherRequestDTO buildTeacherRequestDTO1() {
    return TeacherRequestDTO.builder()
        .id("dd7f5674-3445-4cae-b297-fbcef545aaa6")
        .name("Jan")
        .surname("Kowalski")
        .pesel("75071559492")
        .birthDate(LocalDate.of(1975, 7, 15))
        .placeOfBirth("Tarnobrzeg")
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
        .build();
  }
}
