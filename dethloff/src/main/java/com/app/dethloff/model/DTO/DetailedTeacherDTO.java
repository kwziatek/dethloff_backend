package com.app.dethloff.model.DTO;

import com.app.dethloff.model.Gender;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DetailedTeacherDTO(
        String id,
        String name,
        String surname,
        String pesel,
        String placeOfBirth,
        LocalDate birthDate,
        Gender gender,
        Boolean isActive,
        String city,
        String street,
        String flatNumber,
        String postalCode,
        String phoneNumber,
        String email,
        String companyName,
        String NIP,
        String companySurname,
        String companyCity,
        String companyStreet,
        String companyFlatNumber,
        String companyPostalCode,
        String companyPhoneNumber,
        String companyEmail
) {}
