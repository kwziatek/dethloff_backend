package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.BasicTeacherDTO;
import com.app.dethloff.model.DTO.DetailedTeacherDTO;
import com.app.dethloff.model.TeacherEntity;
import com.app.dethloff.model.pesel.Pesel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherMapper {

    public BasicTeacherDTO toBasicDTO(TeacherEntity teacher) {
        return BasicTeacherDTO.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .pesel(String.valueOf(teacher.getPesel()))
                .isActive(teacher.getIsActive())
                .build();
    }

    public TeacherEntity toTeacher(BasicTeacherDTO teacherDTO) {
        return TeacherEntity.builder()
                .id(teacherDTO.id())
                .name(teacherDTO.name())
                .surname(teacherDTO.surname())
                .build();
    }

    public List<BasicTeacherDTO> toBasicDTO(List<TeacherEntity> teachers) {
        return teachers.stream()
                .map(this::toBasicDTO)
                .toList();
    }

    public List<TeacherEntity> toTeacher(List<BasicTeacherDTO> teacherDTOs) {
        return teacherDTOs.stream()
                .map(this::toTeacher)
                .toList();
    }

    public TeacherEntity detailedToEntity(DetailedTeacherDTO detailedTeacherDTO) {
        return TeacherEntity.builder()
                .id(detailedTeacherDTO.id())
                .name(detailedTeacherDTO.name())
                .surname(detailedTeacherDTO.surname())
                .pesel(new Pesel(detailedTeacherDTO.pesel()))
                .placeOfBirth(detailedTeacherDTO.placeOfBirth())
                .birthDate(detailedTeacherDTO.birthDate())
                .gender(detailedTeacherDTO.gender())
//                .isActive(detailedTeacherDTO.isActive()) // it's being determined in service layer and applied directly to entity
                .city(detailedTeacherDTO.city())
                .street(detailedTeacherDTO.street())
                .flatNumber(detailedTeacherDTO.flatNumber())
                .postalCode(detailedTeacherDTO.postalCode())
                .email(detailedTeacherDTO.email())
                .phoneNumber(detailedTeacherDTO.phoneNumber())
                .companyName(detailedTeacherDTO.companyName())
                .NIP(detailedTeacherDTO.NIP())
                .companyCity(detailedTeacherDTO.companyCity())
                .companyStreet(detailedTeacherDTO.companyStreet())
                .companyFlatNumber(detailedTeacherDTO.companyFlatNumber())
                .companyPostalCode(detailedTeacherDTO.companyPostalCode())
                .companyPhoneNumber(detailedTeacherDTO.companyPhoneNumber())
                .companyEmail(detailedTeacherDTO.companyEmail())
                .build();

    }

    public DetailedTeacherDTO entityToDetailed(TeacherEntity teacherEntity) {
        return DetailedTeacherDTO.builder()
                .id(teacherEntity.getId())
                .name(teacherEntity.getName())
                .surname(teacherEntity.getSurname())
                .pesel(String.valueOf(teacherEntity.getPesel()))
                .birthDate(teacherEntity.getBirthDate())
                .placeOfBirth(teacherEntity.getPlaceOfBirth())
                .gender(teacherEntity.getGender())
                .isActive(teacherEntity.getIsActive())
                .city(teacherEntity.getCity())
                .street(teacherEntity.getStreet())
                .flatNumber(teacherEntity.getFlatNumber())
                .postalCode(teacherEntity.getPostalCode())
                .email(teacherEntity.getEmail())
                .phoneNumber(teacherEntity.getPhoneNumber())
                .companyName(teacherEntity.getCompanyName())
                .NIP(teacherEntity.getNIP())
                .companyCity(teacherEntity.getCompanyCity())
                .companyStreet(teacherEntity.getCompanyStreet())
                .companyFlatNumber(teacherEntity.getCompanyFlatNumber())
                .companyPostalCode(teacherEntity.getCompanyPostalCode())
                .companyPhoneNumber(teacherEntity.getCompanyPhoneNumber())
                .companyEmail(teacherEntity.getCompanyEmail())
                .build();
    }

    public void updateEntityFromDetailed(DetailedTeacherDTO dto, TeacherEntity entity) {
        if (dto == null) {
            return;
        }
        // Update only the fields allowed to be changed
        entity.setName(dto.name());
        entity.setSurname(dto.surname());
        entity.setPesel(dto.pesel()); // Ensure this is the String version now!
        entity.setGender(dto.gender());
        entity.setBirthDate(dto.birthDate());
        entity.setPlaceOfBirth(dto.placeOfBirth());
//        entity.setIsActive(to.isActive()); -> it's calculated in service layer
        entity.setCity(dto.city());
        entity.setStreet(dto.street());
        entity.setFlatNumber(dto.flatNumber());
        entity.setPostalCode(dto.postalCode());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setEmail(dto.email());
        entity.setCompanyName(dto.companyName());
        entity.setNIP(dto.NIP());
        entity.setCompanyCity(dto.companyCity());
        entity.setCompanyStreet(dto.companyStreet());
        entity.setCompanyFlatNumber(dto.companyFlatNumber());
        entity.setCompanyPostalCode(dto.companyPostalCode());
        entity.setCompanyPhoneNumber(dto.companyPhoneNumber());
        entity.setCompanyEmail(dto.companyEmail());

        // Note: We DO NOT set the ID here. The entity already has its ID.
    }
}
