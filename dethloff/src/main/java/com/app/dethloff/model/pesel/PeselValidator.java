package com.app.dethloff.model.pesel;


import com.app.dethloff.exceptions.model.InvalidPeselException;

import java.time.DateTimeException;

public class PeselValidator {
    private static final int[] CONTROL_WEIGHTS = new int[]{1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1};
    private static final PeselDecoder PESEL_DECODER = new PeselDecoder();

    public static boolean isPeselValid(String pesel) {
        try {
            assertIsPeselValid(pesel);
            return true;
        } catch (InvalidPeselException var2) {
            return false;
        }
    }

    public static void assertIsPeselValid(String pesel) {
        assertIsNotNull(pesel);
        assertIsLengthValid(pesel);
        assertIsOnlyDigits(pesel);
        assertIsControlDigitValid(pesel);
        assertIsBirthDateValid(pesel);
    }

    protected static void assertIsNotNull(String pesel) {
        if (pesel == null) {
            throw new NullPointerException("PESEL cannot be null");
        }
    }

    protected static void assertIsLengthValid(String pesel) {
        if (pesel.length() != 11) {
            throw new InvalidPeselException("PESEL length is invalid, should be 11 numbers");
        }
    }

    protected static void assertIsOnlyDigits(String pesel) {
        if (!pesel.matches("[0-9]*")) {
            throw new InvalidPeselException("PESEL contains invalid characters, should contain only digits 0-9");
        }
    }

    protected static void assertIsControlDigitValid(String pesel) {
        if (!isControlDigitValid(pesel)) {
            throw new InvalidPeselException("PESEL control sum number is invalid");
        }
    }

    protected static boolean isControlDigitValid(String pesel) {
        int sum = 0;

        for(int i = 0; i <= 10; ++i) {
            int multipliedNumber = CONTROL_WEIGHTS[i] * Character.getNumericValue(pesel.charAt(i));
            sum += multipliedNumber;
        }

        String controlSum = String.valueOf(sum);
        int lastDigit = Integer.parseInt(controlSum.substring(controlSum.length() - 1));
        return lastDigit == 0;
    }

    protected static void assertIsBirthDateValid(String pesel) {
        if (!isBirthDateValid(pesel)) {
            throw new InvalidPeselException("PESEL birth date is invalid");
        }
    }

    protected static boolean isBirthDateValid(String pesel) {
        try {
            PESEL_DECODER.decodeBirthDate(pesel);
            return true;
        } catch (DateTimeException var2) {
            return false;
        }
    }
}
