package com.app.dethloff.exceptions.model;

public class InvalidLoginOrPasswordException extends RuntimeException {
    public InvalidLoginOrPasswordException(String message) {
        super(message);
    }
}
