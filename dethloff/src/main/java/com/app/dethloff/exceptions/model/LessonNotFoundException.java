package com.app.dethloff.exceptions.model;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(String message) {
        super(message);
    }

    public LessonNotFoundException() {
        super("No such lesson found");
    }
}


