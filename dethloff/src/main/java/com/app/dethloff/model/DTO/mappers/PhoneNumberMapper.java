package com.app.dethloff.model.DTO.mappers;

import org.springframework.stereotype.Component;

@Component
public class PhoneNumberMapper {

    public String toExtendedPhoneNumber(String basicPhoneNumber) {
        if(basicPhoneNumber == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+48");
        for(int i = 0; i <= 2; i++) {
            String trio = basicPhoneNumber.substring(i * 3, i * 3 + 3);
            stringBuilder.append(" ").append(trio);
        }
        return stringBuilder.toString();
    }

    public String toBasicPhoneNumber(String extendedPhoneNumber) {
        if(extendedPhoneNumber == null) {
            return null;
        }
        return extendedPhoneNumber.substring(4).replace(" ", "");
    }
}
