package com.example.userauthservice.exceptions;

public class InvalidCredentialsException extends Exception {
  public InvalidCredentialsException(Exception e) {
    super(e);
  }
}
