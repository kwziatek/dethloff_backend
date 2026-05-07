package com.app.dethloff;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class UUIDGenertor {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            System.out.println(UUID.randomUUID());
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        System.out.println(encoder.encode("0"));
    }
}
