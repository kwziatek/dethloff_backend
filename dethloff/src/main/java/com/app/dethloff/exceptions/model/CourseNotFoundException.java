package com.app.dethloff.exceptions.model;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException() {
        super("No such course found");
    }
}
