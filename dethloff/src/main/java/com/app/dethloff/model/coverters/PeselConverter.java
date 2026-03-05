package com.app.dethloff.model.coverters;


import com.app.dethloff.model.pesel.Pesel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PeselConverter implements AttributeConverter<Pesel, String> {

    @Override
    public String convertToDatabaseColumn(Pesel pesel) {
        String string = String.valueOf(pesel.getBirthDateDigits()) + pesel.getSerialDigits() + pesel.getGenderDigit() + pesel.getControlDigit();
        return string;
    }

    @Override
    public Pesel convertToEntityAttribute(String s) {
        return s == null ? null : new Pesel(s);
    }
}
