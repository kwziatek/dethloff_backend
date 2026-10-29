package com.app.dethloff.rest.mappers;

import com.app.dethloff.rest.DTO.StudentRequestDTO;
import com.app.dethloff.rest.DTO.StudentResponseDTO;
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
        if (dto.guardianName() != null) entity.setGuardianName(dto.guardianName());
        if (dto.guardianSurname() != null) entity.setGuardianSurname(dto.guardianSurname());
        if (dto.guardianCity() != null) entity.setGuardianCity(dto.guardianCity());
        if (dto.guardianStreet() != null) entity.setGuardianStreet(dto.guardianStreet());
        if (dto.guardianFlatNumber() != null) entity.setGuardianFlatNumber(dto.guardianFlatNumber());
        if (dto.guardianPostalCode() != null) entity.setGuardianPostalCode(dto.guardianPostalCode());
        if (basicGuardianPhoneNumber != null) entity.setGuardianPhoneNumber(basicGuardianPhoneNumber);
        if (dto.guardianEmail() != null) entity.setGuardianEmail(dto.guardianEmail());
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
