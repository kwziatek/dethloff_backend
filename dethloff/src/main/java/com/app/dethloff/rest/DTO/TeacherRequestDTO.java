package com.app.dethloff.rest.DTO;

import com.app.dethloff.model.Gender;
import com.app.dethloff.rest.DTO.validation_groups.CreateAction;
import com.app.dethloff.rest.DTO.validation_groups.UpdateAction;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record TeacherRequestDTO(
        @NotNull(groups = UpdateAction.class, message = "STUDENT_NOT_FOUND")
        @Null(groups = CreateAction.class, message = "STUDENT_ID_MUST_BE_NULL")
        String id,
        @Size(
                min = 2,
                groups = {CreateAction.class, UpdateAction.class},
                message = "NAME_MUST_BE_LONGER_THAN_1_CHAR")
        @NotNull(groups = {CreateAction.class})
        String name,
        @Size(
                min = 2,
                groups = {CreateAction.class, UpdateAction.class},
                message = "SURNAME_MUST_BE_LONGER_THAN_1_CHAR")
        @NotNull(groups = {CreateAction.class})
        String surname,
        @NotNull(groups = {CreateAction.class}) String pesel,
        @NotNull(groups = {CreateAction.class}) String placeOfBirth,
        @NotNull(groups = {CreateAction.class}) LocalDate birthDate,
    @Nullable Gender gender,
    @Null Boolean isActive,
    @Nullable String city,
    @Nullable String street,
    @Nullable String flatNumber,
    @Nullable @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "INVALID_POSTAL_CODE")
        String postalCode,
    @Nullable @Pattern(regexp = "\\+48 \\d{3} \\d{3} \\d{3}", message = "INVALID_PHONE_FORMAT")
        String phoneNumber,
    @Email(message = "INVALID_EMAIL") @Nullable String email,
    @Nullable String companyName,
    @Nullable String NIP,
    @Nullable String companySurname,
    @Nullable String companyCity,
    @Nullable String companyStreet,
    @Nullable String companyFlatNumber,
    @Nullable @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "INVALID_POSTAL_CODE")
        String companyPostalCode,
    @Nullable @Pattern(regexp = "\\+48 \\d{3} \\d{3} \\d{3}", message = "INVALID_PHONE_FORMAT")
        String companyPhoneNumber,
    @Nullable @Email(message = "INVALID_EMAIL") String companyEmail,
    @Nullable String marketingSources) {}
