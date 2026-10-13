package com.app.dethloff.model.DTO;

import com.app.dethloff.model.Gender;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TeacherRequestDTO(
        @NotNull
        String id,
        @NotNull
        String name,
        @NotNull
        String surname,
        @Nullable
        String pesel,
        @Nullable
        String placeOfBirth,
        @Nullable
        LocalDate birthDate,
        @Nullable
        Gender gender,
        @Nullable
        Boolean isActive,
        @Nullable
        String city,
        @Nullable
        String street,
        @Nullable
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
        @Nullable
        String companyName,
        @Nullable
        String NIP,
        @Nullable
        String companySurname,
        @Nullable
        String companyCity,
        @Nullable
        String companyStreet,
        @Nullable
        String companyFlatNumber,
        @Nullable
        @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "Invalid postal code")
        String companyPostalCode,
        @Nullable
        @Pattern(regexp = "\\+48 \\d{3} \\d{3} \\d{3}", message = "Invalid phone format")
        String companyPhoneNumber,
        @Nullable
        @Email(message = "Invalid email")
        String companyEmail,
        @Nullable
        String marketingSources
) {}
