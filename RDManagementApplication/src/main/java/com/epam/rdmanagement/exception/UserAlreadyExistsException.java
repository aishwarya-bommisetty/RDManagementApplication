package com.epam.rdmanagement.exception;

public class UserAlreadyExistsException extends Exception {
  private static final long serialVersionUID = -2814621056343737820L;

  public UserAlreadyExistsException(String message) {
    super(message);
  }
}

