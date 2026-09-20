package com.example.userauthservice.exceptions;

public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException(String message) {
    super(message);
  }
}
