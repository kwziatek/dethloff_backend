package com.app.dethloff.model.pesel;


import java.time.LocalDate;


public class PeselDecoder {
    LocalDate decodeBirthDate(String pesel) {
        int year = 1900;
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));
        int monthFirstDigit = Character.getNumericValue(pesel.charAt(2));
        switch (monthFirstDigit) {
            case 2:
            case 3:
                month -= 20;
                year = 2000;
                break;
            case 4:
            case 5:
                month -= 40;
                year = 2100;
                break;
            case 6:
            case 7:
                month -= 60;
                year = 2200;
                break;
            case 8:
            case 9:
                month -= 80;
                year = 1800;
        }

        year += Integer.parseInt(pesel.substring(0, 2));
        return LocalDate.of(year, month, day);
    }

    Pesel.Gender decodeGender(String pesel) {
        int genderDigit = Character.getNumericValue(pesel.charAt(9));
        return genderDigit % 2 == 0 ? Pesel.Gender.FEMALE : Pesel.Gender.MALE;
    }
}
