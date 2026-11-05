package com.app.dethloff.exceptions.model;

public class StudentAlreadyEnrolledException extends RuntimeException {
  public StudentAlreadyEnrolledException(String message) {
    super(message);
  }

  public StudentAlreadyEnrolledException() {
    super("STUDENT_ALREADY_ENROLLED");
  }
}
