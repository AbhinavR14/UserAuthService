package com.example.userauthservice.exceptions;

public class InvalidUserOperationException extends RuntimeException {
  public InvalidUserOperationException(String message) {
    super(message);
  }
}
