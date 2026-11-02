package com.app.dethloff.exceptions.model;

public class InvalidLoginOrPasswordException extends RuntimeException {
  public InvalidLoginOrPasswordException(String message) {
    super(message);
  }
  public InvalidLoginOrPasswordException() {
    super("WRONG_USERNAME_OR_PASSWORD");
  }
}
