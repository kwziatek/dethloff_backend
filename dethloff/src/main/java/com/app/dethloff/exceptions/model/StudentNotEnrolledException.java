package com.app.dethloff.exceptions.model;

public class StudentNotEnrolledException extends RuntimeException {
  public StudentNotEnrolledException(String message) {
    super(message);
  }

  public StudentNotEnrolledException() {
    super("STUDENT_NOT_ENROLLED");
  }
}
