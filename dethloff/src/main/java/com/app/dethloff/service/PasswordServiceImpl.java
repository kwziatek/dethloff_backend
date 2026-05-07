package com.app.dethloff.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordServiceImpl implements PasswordService{

    int minLength = 14;
    int minSpecialSigns = 3;


    @Override
    public boolean validateNewPassword(String password) {
        return isLengthValid(password) && containsUppercaseLetter(password) && containsSpecialSigns(password);
    }

    private boolean isLengthValid(String password) {
         return password.length() >= minLength;
    }

    private boolean containsUppercaseLetter(String password) {
        for(int i = 0; i < password.length(); i++) {
            if(Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSpecialSigns(String password) {
        List<Character> specialSigns = List.of('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '[', ']', '{', '}', ';', ':', '/', '?', ',', '.');
        int counter = 0;
        for(int i = 0; i < password.length(); i++) {
            for(char c: specialSigns) {
                if (password.charAt(i) == c) {
                    counter++;
                    if(counter == minSpecialSigns) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
