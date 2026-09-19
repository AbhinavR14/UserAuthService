package com.example.userauthservice.exceptions;

public class InvalidCredentialsException extends Throwable {
  public InvalidCredentialsException(Exception e) {
    super(e);
  }
}
