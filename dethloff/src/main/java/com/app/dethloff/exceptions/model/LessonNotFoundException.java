package com.app.dethloff.exceptions.model;

public class LessonNotFoundException extends RuntimeException {
  public LessonNotFoundException(String message) {
    super(message);
  }

  public LessonNotFoundException() {
    super("LESSON_NOT_FOUND");
  }
}
