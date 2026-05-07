package com.app.dethloff.exceptions.model;

public class SetOfCoursesNotFoundException extends RuntimeException {
    public SetOfCoursesNotFoundException(String message) {
        super(message);
    }

    public SetOfCoursesNotFoundException() {
        super("No such set of courses found");
    }
}
