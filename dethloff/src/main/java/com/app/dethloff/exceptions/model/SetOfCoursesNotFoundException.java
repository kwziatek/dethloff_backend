package com.app.dethloff.exceptions.model;

public class SetOfCoursesNotFoundException extends RuntimeException {
    public SetOfCoursesNotFoundException(String message) {
        super(message);
    }

    public SetOfCoursesNotFoundException() {
        super("SETOFCOURSES_NOT_FOUND");
    }
}
