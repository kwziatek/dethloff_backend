package com.app.dethloff.exceptions.model;

public class TeacherNotFoundException extends RuntimeException {
  public TeacherNotFoundException(String message) {
    super(message);
  }

  public TeacherNotFoundException() {
    super("TEACHER_NOT_FOUND");
  }
}
