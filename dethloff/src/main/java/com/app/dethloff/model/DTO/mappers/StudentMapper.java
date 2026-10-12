package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.StudentRequestDTO;
import com.app.dethloff.model.DTO.StudentResponseDTO;
import com.app.dethloff.model.StudentEntity;
import com.app.dethloff.model.pesel.Pesel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMapper {

    private final PhoneNumberMapper phoneNumberMapper;

    @Autowired
    public StudentMapper(PhoneNumberMapper phoneNumberMapper) {
        this.phoneNumberMapper = phoneNumberMapper;
    }


    public StudentEntity toEntity(StudentRequestDTO studentRequestDTO) {
        String basicPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(studentRequestDTO.phoneNumber());
        String basicGuardianPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(studentRequestDTO.guardianPhoneNumber());
        String basicCompanyPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(studentRequestDTO.companyPhoneNumber());
        return StudentEntity.builder()
                .id(studentRequestDTO.id())
                .name(studentRequestDTO.name())
                .surname(studentRequestDTO.surname())
                .pesel(new Pesel(studentRequestDTO.pesel()))
                .placeOfBirth(studentRequestDTO.placeOfBirth())
                .birthDate(studentRequestDTO.birthDate())
                .gender(studentRequestDTO.gender())
                .isActive(studentRequestDTO.isActive())
                .city(studentRequestDTO.city())
                .street(studentRequestDTO.street())
                .flatNumber(studentRequestDTO.flatNumber())
                .postalCode(studentRequestDTO.postalCode())
                .email(studentRequestDTO.email())
                .phoneNumber(basicPhoneNumber)
                .guardianName(studentRequestDTO.guardianName())
                .guardianSurname(studentRequestDTO.guardianSurname())
                .guardianCity(studentRequestDTO.guardianCity())
                .guardianStreet(studentRequestDTO.guardianStreet())
                .guardianFlatNumber(studentRequestDTO.guardianFlatNumber())
                .guardianPostalCode(studentRequestDTO.guardianPostalCode())
                .guardianPhoneNumber(basicGuardianPhoneNumber)
                .guardianEmail(studentRequestDTO.guardianEmail())
                .companyName(studentRequestDTO.companyName())
                .NIP(studentRequestDTO.NIP())
                .companyCity(studentRequestDTO.companyCity())
                .companyStreet(studentRequestDTO.companyStreet())
                .companyFlatNumber(studentRequestDTO.companyFlatNumber())
                .companyPostalCode(studentRequestDTO.companyPostalCode())
                .companyPhoneNumber(basicCompanyPhoneNumber)
                .companyEmail(studentRequestDTO.companyEmail())
                .marketingSources(studentRequestDTO.marketingSources())
                .build();
    }

    public List<StudentEntity> toEntity(List<StudentRequestDTO> studentRequestDTOList) {
        return studentRequestDTOList.stream()
            .map(this::toEntity)
            .toList();
    }

    public StudentResponseDTO toStudentResponseDTO(StudentEntity studentEntity) {
        String extendedPhoneNumber = phoneNumberMapper.toExtendedPhoneNumber(studentEntity.getPhoneNumber());
        String extendedGuardianPhoneNumber = phoneNumberMapper.toExtendedPhoneNumber(studentEntity.getGuardianPhoneNumber());
        String extendedCompanyPhoneNumber = phoneNumberMapper.toExtendedPhoneNumber(studentEntity.getCompanyPhoneNumber());
        return StudentResponseDTO.builder()
                .id(studentEntity.getId())
                .name(studentEntity.getName())
                .surname(studentEntity.getSurname())
                .pesel(String.valueOf(studentEntity.getPesel()))
                .birthDate(studentEntity.getBirthDate())
                .placeOfBirth(studentEntity.getPlaceOfBirth())
                .gender(studentEntity.getGender())
                .isActive(studentEntity.getIsActive())
                .city(studentEntity.getCity())
                .street(studentEntity.getStreet())
                .flatNumber(studentEntity.getFlatNumber())
                .postalCode(studentEntity.getPostalCode())
                .email(studentEntity.getEmail())
                .phoneNumber(extendedPhoneNumber)
                .guardianName(studentEntity.getGuardianName())
                .guardianSurname(studentEntity.getGuardianSurname())
                .guardianCity(studentEntity.getGuardianCity())
                .guardianStreet(studentEntity.getGuardianStreet())
                .guardianFlatNumber(studentEntity.getGuardianFlatNumber())
                .guardianPostalCode(studentEntity.getGuardianPostalCode())
                .guardianPhoneNumber(extendedGuardianPhoneNumber)
                .guardianEmail(studentEntity.getGuardianEmail())
                .companyName(studentEntity.getCompanyName())
                .NIP(studentEntity.getNIP())
                .companyCity(studentEntity.getCompanyCity())
                .companyStreet(studentEntity.getCompanyStreet())
                .companyFlatNumber(studentEntity.getCompanyFlatNumber())
                .companyPostalCode(studentEntity.getCompanyPostalCode())
                .companyPhoneNumber(extendedCompanyPhoneNumber)
                .companyEmail(studentEntity.getCompanyEmail())
                .marketingSources(studentEntity.getMarketingSources())
                .build();
    }

    public List<StudentResponseDTO> toStudentResponseDTO(List<StudentEntity> studentEntities) {
        return studentEntities.stream()
                .map(this::toStudentResponseDTO)
                .toList();
    }

    public void updateEntityFromRequest(StudentRequestDTO dto, StudentEntity entity) {
        if (dto == null){
            return;
        }
        String basicPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(dto.phoneNumber());
        String basicGuardianPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(dto.guardianPhoneNumber());
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
        entity.setGuardianName(dto.guardianName());
        entity.setGuardianSurname(dto.guardianSurname());
        entity.setGuardianCity(dto.guardianCity());
        entity.setGuardianStreet(dto.guardianStreet());
        entity.setGuardianFlatNumber(dto.guardianFlatNumber());
        entity.setGuardianPostalCode(dto.guardianPostalCode());
        entity.setGuardianPhoneNumber(basicGuardianPhoneNumber);
        entity.setGuardianEmail(dto.guardianEmail());
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
