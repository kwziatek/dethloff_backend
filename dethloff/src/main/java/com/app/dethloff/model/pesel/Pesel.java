package com.app.dethloff.model.pesel;


import com.app.dethloff.exceptions.model.InvalidPeselException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Pesel {
    private LocalDate birthDate;
    private Pesel.Gender gender;

    private String birthDateDigits;
    private String serialDigits;
    private Character genderDigit;
    private Character controlDigit;

    public Pesel(String pesel) throws InvalidPeselException {
        this(new PeselDecoder(), pesel);
    }

    private Pesel(PeselDecoder peselDecoder, String pesel) throws InvalidPeselException{
        PeselValidator.assertIsPeselValid(pesel);
        this.birthDate = peselDecoder.decodeBirthDate(pesel);
        this.gender = peselDecoder.decodeGender(pesel);
        this.setBirthDateDigits(pesel);
        this.setSerialDigits(pesel);
        this.setGenderDigit(pesel);
        this.setControlDigit(pesel);
    }

     enum Gender {
        MALE,
        FEMALE
    }

    void setBirthDateDigits(String pesel) {
        this.birthDateDigits = pesel.substring(0, 6);
    }

    void setSerialDigits(String pesel) {
        this.serialDigits = pesel.substring(6, 9);
    }

    void setGenderDigit(String pesel) {
        this.genderDigit = pesel.charAt(9);
    }

    void setControlDigit(String pesel) {
        this.controlDigit = pesel.charAt(10);
    }

    @Override
    public String toString() {
        return this.birthDateDigits + this.serialDigits + this.genderDigit + this.controlDigit;
    }
}
