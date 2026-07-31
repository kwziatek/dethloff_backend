package com.app.dethloff.model.DTO;

import com.app.dethloff.model.Gender;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DetailedStudentDTO(
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
    @Nullable
    @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "Invalid postal code")
    String postalCode,
    @Nullable
    @Pattern(regexp = "\\+48 \\d{3} \\d{3} \\d{3}", message = "Invalid phone format")
    String phoneNumber,
    @Email(message = "Invalid email")
    @Nullable
    String email,
    String guardianName,
    String guardianSurname,
    String guardianCity,
    String guardianStreet,
    String guardianFlatNumber,
    String guardianPostalCode,
    String guardianPhoneNumber,
    String guardianEmail,
    String companyName,
    String NIP,
    String companySurname,
    String companyCity,
    String companyStreet,
    String companyFlatNumber,
    String companyPostalCode,
    String companyPhoneNumber,
    String companyEmail,
    String marketingSources
) {
}
