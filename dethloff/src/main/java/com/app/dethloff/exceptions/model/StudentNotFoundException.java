package com.app.dethloff.exceptions.model;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException() {
        super("No such student found");
    }
}
