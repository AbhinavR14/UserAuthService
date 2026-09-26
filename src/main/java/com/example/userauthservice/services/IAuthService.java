package com.example.userauthservice.services;

import com.example.userauthservice.models.UserSession;
import com.example.userauthservice.exceptions.InvalidCredentialsException;
import com.example.userauthservice.models.User;

public interface IAuthService {
  User signup(String name, String email, String password, String phoneNumber);

  UserSession login(String email, String password) throws InvalidCredentialsException;
}
