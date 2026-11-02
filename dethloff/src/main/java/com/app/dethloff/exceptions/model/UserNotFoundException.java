package com.app.dethloff.exceptions.model;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException() {
    super("USER_NOT_FOUND");
  }
}
