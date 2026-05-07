package com.app.dethloff.exceptions.model;


public class TeacherNotFoundException extends RuntimeException{
    public TeacherNotFoundException(String message) {
        super(message);
    }

    public TeacherNotFoundException() {
        super("No such teacher found");
    }
}
