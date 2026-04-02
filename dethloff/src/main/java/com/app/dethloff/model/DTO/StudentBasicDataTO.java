package com.app.dethloff.model.DTO;

import com.app.dethloff.model.Gender;
import com.app.dethloff.model.pesel.Pesel;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StudentBasicDataTO(
        String id,
        String name,
        String surname,
        Boolean isActive,
        String pesel,
        LocalDate birthDate,
        String placeOfBirth,
        Gender gender
) {

}
