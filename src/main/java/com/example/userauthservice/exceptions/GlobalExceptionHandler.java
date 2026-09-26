package com.example.userauthservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException exception) {
    return new ResponseEntity<>(
            exception.getMessage(),
            HttpStatus.CONFLICT
    );
  }

  @ExceptionHandler(InvalidRoleOperationException.class)
  public ResponseEntity<String> handleInvalidRoleOperation(InvalidRoleOperationException exception) {
    return new ResponseEntity<>(
            exception.getMessage(),
            HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(RoleAlreadyExistsException.class)
  public ResponseEntity<String> handleRoleAlreadyExists(RoleAlreadyExistsException exception) {
    return new ResponseEntity<>(
            exception.getMessage(),
            HttpStatus.CONFLICT
    );
  }

  @ExceptionHandler(RoleNotFoundException.class)
  public ResponseEntity<String> handleRoleNotFound(RoleNotFoundException exception) {
    return new ResponseEntity<>(
            exception.getMessage(),
            HttpStatus.NOT_FOUND
    );
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException exception) {
    return new ResponseEntity<>(
            exception.getCause().getMessage(),
            HttpStatus.UNAUTHORIZED
    );
  }

}
