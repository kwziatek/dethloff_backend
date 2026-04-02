package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.DetailedStudentDTO;
import com.app.dethloff.model.DTO.StudentBasicDataTO;
import com.app.dethloff.model.DTO.StudentResponseDTO;
import com.app.dethloff.model.StudentEntity;
import com.app.dethloff.model.pesel.Pesel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMapper {
    public StudentMapper() {

    }
    public StudentResponseDTO toDTO(StudentEntity student) {
        String pesel = student.getPesel() != null ? student.getPesel().toString() : null;

        return StudentResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .isActive(student.getIsActive())
                .pesel(pesel)
                .birthDate(student.getBirthDate())
                .placeOfBirth(student.getPlaceOfBirth())
                .gender(student.getGender())
                .build();
    }


    public List<StudentResponseDTO> toDTO(List<StudentEntity> students) {
        return students.stream()
                .map(this::toDTO)
                .toList();
    }

    public StudentBasicDataTO toBasicTo(StudentEntity studentEntity) {
        return StudentBasicDataTO.builder()
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

    public StudentEntity toEntity(StudentBasicDataTO studentBasicDataTO) {
        return StudentEntity.builder()
                .id(studentBasicDataTO.id())
                .name(studentBasicDataTO.name())
                .surname(studentBasicDataTO.surname())
                .pesel(new Pesel(studentBasicDataTO.pesel()))
                .placeOfBirth(studentBasicDataTO.placeOfBirth())
                .birthDate(studentBasicDataTO.birthDate())
                .isActive(studentBasicDataTO.isActive())
                .gender(studentBasicDataTO.gender())
                .build();

    }

    public void updateEntityFromDetailed(DetailedStudentDTO to, StudentEntity entity) {
        if (to == null) return;

        // Update only the fields allowed to be changed
        entity.setName(to.name());
        entity.setSurname(to.surname());
        entity.setPesel(to.pesel()); // Ensure this is the String version now!
        entity.setGender(to.gender());
        entity.setBirthDate(to.birthDate());
        entity.setPlaceOfBirth(to.placeOfBirth());
//        entity.setIsActive(to.isActive()); -> it's calculated in service layer
        entity.setCity(to.city());
        entity.setStreet(to.street());
        entity.setFlatNumber(to.flatNumber());
        entity.setPostalCode(to.postalCode());
        entity.setPhoneNumber(to.phoneNumber());
        entity.setEmail(to.email());
        entity.setGuardianName(to.guardianName());
        entity.setGuardianSurname(to.guardianSurname());
        entity.setGuardianCity(to.guardianCity());
        entity.setGuardianStreet(to.guardianStreet());
        entity.setGuardianFlatNumber(to.guardianFlatNumber());
        entity.setGuardianPostalCode(to.guardianPostalCode());
        entity.setGuardianPhoneNumber(to.guardianPhoneNumber());
        entity.setGuardianEmail(to.guardianEmail());
        entity.setCompanyName(to.companyName());
        entity.setNIP(to.NIP());
        entity.setCompanyCity(to.companyCity());
        entity.setCompanyStreet(to.companyStreet());
        entity.setCompanyFlatNumber(to.companyFlatNumber());
        entity.setCompanyPostalCode(to.companyPostalCode());
        entity.setCompanyPhoneNumber(to.companyPhoneNumber());
        entity.setCompanyEmail(to.companyEmail());
        entity.setMarketingSources(to.marketingSources());

        // Note: We DO NOT set the ID here. The entity already has its ID.
    }



    public StudentEntity detailedToEntity(DetailedStudentDTO detailedStudentDTO) {
        return StudentEntity.builder()
                .id(detailedStudentDTO.id())
                .name(detailedStudentDTO.name())
                .surname(detailedStudentDTO.surname())
                .pesel(new Pesel(detailedStudentDTO.pesel()))
                .placeOfBirth(detailedStudentDTO.placeOfBirth())
                .birthDate(detailedStudentDTO.birthDate())
                .gender(detailedStudentDTO.gender())
//                .isActive(detailedStudentDTO.isActive()) // it's being determined in service layer and applied directly to entity
                .city(detailedStudentDTO.city())
                .street(detailedStudentDTO.street())
                .flatNumber(detailedStudentDTO.flatNumber())
                .postalCode(detailedStudentDTO.postalCode())
                .email(detailedStudentDTO.email())
                .phoneNumber(detailedStudentDTO.phoneNumber())
                .guardianName(detailedStudentDTO.guardianName())
                .guardianSurname(detailedStudentDTO.guardianSurname())
                .guardianCity(detailedStudentDTO.guardianCity())
                .guardianStreet(detailedStudentDTO.guardianStreet())
                .guardianFlatNumber(detailedStudentDTO.guardianFlatNumber())
                .guardianPostalCode(detailedStudentDTO.guardianPostalCode())
                .guardianPhoneNumber(detailedStudentDTO.guardianPhoneNumber())
                .guardianEmail(detailedStudentDTO.guardianEmail())
                .companyName(detailedStudentDTO.companyName())
                .NIP(detailedStudentDTO.NIP())
                .companyCity(detailedStudentDTO.companyCity())
                .companyStreet(detailedStudentDTO.companyStreet())
                .companyFlatNumber(detailedStudentDTO.companyFlatNumber())
                .companyPostalCode(detailedStudentDTO.companyPostalCode())
                .companyPhoneNumber(detailedStudentDTO.companyPhoneNumber())
                .companyEmail(detailedStudentDTO.companyEmail())
                .marketingSources(detailedStudentDTO.marketingSources())
                .build();

    }

    public DetailedStudentDTO entityToDetailed(StudentEntity studentEntity) {
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
                .phoneNumber(studentEntity.getPhoneNumber())
                .guardianName(studentEntity.getGuardianName())
                .guardianSurname(studentEntity.getGuardianSurname())
                .guardianCity(studentEntity.getGuardianCity())
                .guardianStreet(studentEntity.getGuardianStreet())
                .guardianFlatNumber(studentEntity.getGuardianFlatNumber())
                .guardianPostalCode(studentEntity.getGuardianPostalCode())
                .guardianPhoneNumber(studentEntity.getGuardianPhoneNumber())
                .guardianEmail(studentEntity.getGuardianEmail())
                .companyName(studentEntity.getCompanyName())
                .NIP(studentEntity.getNIP())
                .companyCity(studentEntity.getCompanyCity())
                .companyStreet(studentEntity.getCompanyStreet())
                .companyFlatNumber(studentEntity.getCompanyFlatNumber())
                .companyPostalCode(studentEntity.getCompanyPostalCode())
                .companyPhoneNumber(studentEntity.getCompanyPhoneNumber())
                .companyEmail(studentEntity.getCompanyEmail())
                .marketingSources(studentEntity.getMarketingSources())
                .build();
    }
}
