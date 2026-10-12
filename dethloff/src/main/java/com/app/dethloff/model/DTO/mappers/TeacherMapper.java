package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.TeacherRequestDTO;
import com.app.dethloff.model.DTO.TeacherResponseDTO;
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

        entity.setName(dto.name());
        entity.setSurname(dto.surname());
        entity.setPesel(dto.pesel());
        entity.setGender(dto.gender());
        entity.setBirthDate(dto.birthDate());
        entity.setPlaceOfBirth(dto.placeOfBirth());
        entity.setCity(dto.city());
        entity.setStreet(dto.street());
        entity.setFlatNumber(dto.flatNumber());
        entity.setPostalCode(dto.postalCode());
        entity.setPhoneNumber(basicPhoneNumber);
        entity.setEmail(dto.email());
        entity.setCompanyName(dto.companyName());
        entity.setNIP(dto.NIP());
        entity.setCompanyCity(dto.companyCity());
        entity.setCompanyStreet(dto.companyStreet());
        entity.setCompanyFlatNumber(dto.companyFlatNumber());
        entity.setCompanyPostalCode(dto.companyPostalCode());
        entity.setCompanyPhoneNumber(basicCompanyPhoneNumber);
        entity.setCompanyEmail(dto.companyEmail());
        entity.setMarketingSources(dto.marketingSources());
    }
}
