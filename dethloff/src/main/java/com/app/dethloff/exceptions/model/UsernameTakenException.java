package com.app.dethloff.exceptions.model;

public class UsernameTakenException extends RuntimeException {
  public UsernameTakenException(String message) {
    super(message);
  }
  public UsernameTakenException() {
    super("USERNAME_ALREADY_TAKEN");
  }
}
