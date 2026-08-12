package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.BasicStudentDTO;
import com.app.dethloff.model.DTO.DetailedStudentDTO;
import com.app.dethloff.model.StudentEntity;
import com.app.dethloff.model.pesel.Pesel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentMapper {

    PhoneNumberMapper phoneNumberMapper;

    @Autowired
    public StudentMapper(PhoneNumberMapper phoneNumberMapper) {
        this.phoneNumberMapper = phoneNumberMapper;
    }

    public BasicStudentDTO toBasicDTO(StudentEntity studentEntity) {
        return BasicStudentDTO.builder()
                .id(studentEntity.getId())
                .name(studentEntity.getName())
                .surname(studentEntity.getSurname())
                .pesel(String.valueOf(studentEntity.getPesel()))
                .placeOfBirth(studentEntity.getPlaceOfBirth())
                .birthDate(studentEntity.getBirthDate())
                .isActive(studentEntity.getIsActive())
                .gender(studentEntity.getGender())
                .build();
    }

    public List<BasicStudentDTO> toBasicDTO(List<StudentEntity> studentEntities) {
        List<BasicStudentDTO> basicEntities = new ArrayList<>();
        for(StudentEntity entity: studentEntities) {
            basicEntities.add(this.toBasicDTO(entity));
        }
        return basicEntities;
    }

    public StudentEntity toEntity(BasicStudentDTO basicStudentDTO) {
        return StudentEntity.builder()
                .id(basicStudentDTO.id())
                .name(basicStudentDTO.name())
                .surname(basicStudentDTO.surname())
                .pesel(new Pesel(basicStudentDTO.pesel()))
                .placeOfBirth(basicStudentDTO.placeOfBirth())
                .birthDate(basicStudentDTO.birthDate())
                .isActive(basicStudentDTO.isActive())
                .gender(basicStudentDTO.gender())
                .build();

    }

    public void updateEntityFromDetailed(DetailedStudentDTO dto, StudentEntity entity) {
        if (dto == null){
            return;
        }
        String basicPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(dto.phoneNumber());
        String basicGuardianPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(dto.guardianPhoneNumber());
        String basicCompanyPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(dto.companyPhoneNumber());

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

        // Note: We DO NOT set the ID here. The entity already has its ID.
    }



    public StudentEntity detailedToEntity(DetailedStudentDTO detailedStudentDTO) {
        String basicPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(detailedStudentDTO.phoneNumber());
        String basicGuardianPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(detailedStudentDTO.guardianPhoneNumber());
        String basicCompanyPhoneNumber = phoneNumberMapper.toBasicPhoneNumber(detailedStudentDTO.companyPhoneNumber());
        return StudentEntity.builder()
                .id(detailedStudentDTO.id())
                .name(detailedStudentDTO.name())
                .surname(detailedStudentDTO.surname())
                .pesel(new Pesel(detailedStudentDTO.pesel()))
                .placeOfBirth(detailedStudentDTO.placeOfBirth())
                .birthDate(detailedStudentDTO.birthDate())
                .gender(detailedStudentDTO.gender())
//                .isActive(detailedStudentDTO.isActive()) // it's being determined in the service layer and applied directly to the entity
                .city(detailedStudentDTO.city())
                .street(detailedStudentDTO.street())
                .flatNumber(detailedStudentDTO.flatNumber())
                .postalCode(detailedStudentDTO.postalCode())
                .email(detailedStudentDTO.email())
                .phoneNumber(basicPhoneNumber)
                .guardianName(detailedStudentDTO.guardianName())
                .guardianSurname(detailedStudentDTO.guardianSurname())
                .guardianCity(detailedStudentDTO.guardianCity())
                .guardianStreet(detailedStudentDTO.guardianStreet())
                .guardianFlatNumber(detailedStudentDTO.guardianFlatNumber())
                .guardianPostalCode(detailedStudentDTO.guardianPostalCode())
                .guardianPhoneNumber(basicGuardianPhoneNumber)
                .guardianEmail(detailedStudentDTO.guardianEmail())
                .companyName(detailedStudentDTO.companyName())
                .NIP(detailedStudentDTO.NIP())
                .companyCity(detailedStudentDTO.companyCity())
                .companyStreet(detailedStudentDTO.companyStreet())
                .companyFlatNumber(detailedStudentDTO.companyFlatNumber())
                .companyPostalCode(detailedStudentDTO.companyPostalCode())
                .companyPhoneNumber(basicCompanyPhoneNumber)
                .companyEmail(detailedStudentDTO.companyEmail())
                .marketingSources(detailedStudentDTO.marketingSources())
                .build();

    }

    public DetailedStudentDTO entityToDetailed(StudentEntity studentEntity) {
        String extendedPhoneNumber = phoneNumberMapper.toExtendedPhoneNumber(studentEntity.getPhoneNumber());
        String extendedGuardianPhoneNumber = phoneNumberMapper.toExtendedPhoneNumber(studentEntity.getGuardianPhoneNumber());
        String extendedCompanyPhoneNumber = phoneNumberMapper.toExtendedPhoneNumber(studentEntity.getCompanyPhoneNumber());
        return DetailedStudentDTO.builder()
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
}
