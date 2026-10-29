package com.app.dethloff.rest.mappers;

import com.app.dethloff.rest.DTO.TeacherRequestDTO;
import com.app.dethloff.rest.DTO.TeacherResponseDTO;
import com.app.dethloff.model.TeacherEntity;
import com.app.dethloff.model.pesel.Pesel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherMapper {

    private final PhoneNumberMapper phoneNumberMapper;

    @Autowired
    public TeacherMapper(PhoneNumberMapper phoneNumberMapper) {
        this.phoneNumberMapper = phoneNumberMapper;
    }


    public TeacherEntity toEntity(TeacherRequestDTO teacherRequestDTO) {
        String basicPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(teacherRequestDTO.phoneNumber());
        String basicCompanyPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(teacherRequestDTO.companyPhoneNumber());
        return TeacherEntity.builder()
                .id(teacherRequestDTO.id())
                .name(teacherRequestDTO.name())
                .surname(teacherRequestDTO.surname())
                .pesel(new Pesel(teacherRequestDTO.pesel()))
                .placeOfBirth(teacherRequestDTO.placeOfBirth())
                .birthDate(teacherRequestDTO.birthDate())
                .gender(teacherRequestDTO.gender())
                .isActive(teacherRequestDTO.isActive())
                .city(teacherRequestDTO.city())
                .street(teacherRequestDTO.street())
                .flatNumber(teacherRequestDTO.flatNumber())
                .postalCode(teacherRequestDTO.postalCode())
                .email(teacherRequestDTO.email())
                .phoneNumber(basicPhoneNumber)
                .companyName(teacherRequestDTO.companyName())
                .NIP(teacherRequestDTO.NIP())
                .companyCity(teacherRequestDTO.companyCity())
                .companyStreet(teacherRequestDTO.companyStreet())
                .companyFlatNumber(teacherRequestDTO.companyFlatNumber())
                .companyPostalCode(teacherRequestDTO.companyPostalCode())
                .companyPhoneNumber(basicCompanyPhoneNumber)
                .companyEmail(teacherRequestDTO.companyEmail())
                .marketingSources(teacherRequestDTO.marketingSources())
                .build();
    }

    public List<TeacherEntity> toEntity(List<TeacherRequestDTO> teacherRequestDTOList) {
        return teacherRequestDTOList.stream()
                .map(this::toEntity)
                .toList();
    }

    public TeacherResponseDTO toTeacherResponseDTO(TeacherEntity teacherEntity) {
        String extendedPhoneNumber = phoneNumberMapper.toExtendedPhoneNumber(teacherEntity.getPhoneNumber());
        String extendedCompanyPhoneNumber = phoneNumberMapper.toExtendedPhoneNumber(teacherEntity.getCompanyPhoneNumber());
        return TeacherResponseDTO.builder()
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
                .phoneNumber(extendedPhoneNumber)
                .companyName(teacherEntity.getCompanyName())
                .NIP(teacherEntity.getNIP())
                .companyCity(teacherEntity.getCompanyCity())
                .companyStreet(teacherEntity.getCompanyStreet())
                .companyFlatNumber(teacherEntity.getCompanyFlatNumber())
                .companyPostalCode(teacherEntity.getCompanyPostalCode())
                .companyPhoneNumber(extendedCompanyPhoneNumber)
                .companyEmail(teacherEntity.getCompanyEmail())
                .marketingSources(teacherEntity.getMarketingSources())
                .build();
    }

    public List<TeacherResponseDTO> toTeacherResponseDTO(List<TeacherEntity> teacherEntities) {
        return teacherEntities.stream()
                .map(this::toTeacherResponseDTO)
                .toList();
    }

    public void updateEntityFromRequest(TeacherRequestDTO dto, TeacherEntity entity) {
        if (dto == null){
            return;
        }
        String basicPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(dto.phoneNumber());
        String basicCompanyPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(dto.companyPhoneNumber());

        if (dto.name() != null) entity.setName(dto.name());
        if (dto.surname() != null) entity.setSurname(dto.surname());
        if (dto.pesel() != null) entity.setPesel(dto.pesel());
        if (dto.gender() != null) entity.setGender(dto.gender());
        if (dto.birthDate() != null) entity.setBirthDate(dto.birthDate());
        if (dto.placeOfBirth() != null) entity.setPlaceOfBirth(dto.placeOfBirth());
        if (dto.city() != null) entity.setCity(dto.city());
        if (dto.street() != null) entity.setStreet(dto.street());
        if (dto.flatNumber() != null) entity.setFlatNumber(dto.flatNumber());
        if (dto.postalCode() != null) entity.setPostalCode(dto.postalCode());
        if (basicPhoneNumber != null) entity.setPhoneNumber(basicPhoneNumber);
        if (dto.email() != null) entity.setEmail(dto.email());
        if (dto.companyName() != null) entity.setCompanyName(dto.companyName());
        if (dto.NIP() != null) entity.setNIP(dto.NIP());
        if (dto.companyCity() != null) entity.setCompanyCity(dto.companyCity());
        if (dto.companyStreet() != null) entity.setCompanyStreet(dto.companyStreet());
        if (dto.companyFlatNumber() != null) entity.setCompanyFlatNumber(dto.companyFlatNumber());
        if (dto.companyPostalCode() != null) entity.setCompanyPostalCode(dto.companyPostalCode());
        if (basicCompanyPhoneNumber != null) entity.setCompanyPhoneNumber(basicCompanyPhoneNumber);
        if (dto.companyEmail() != null) entity.setCompanyEmail(dto.companyEmail());
        if (dto.marketingSources() != null) entity.setMarketingSources(dto.marketingSources());
    }
}
