package com.example.userauthservice.services;

import com.example.userauthservice.models.Status;
import com.example.userauthservice.models.User;

import java.util.List;

public interface IUserService {

  User getUserDetails(long id);
  List<User> getAllUsers(Status status);

  User assignRole(Long userId, Long roleId);
  User removeRole(Long userId, Long roleId);

  void deleteUser(Long userId);

  User activateUser(Long userId);
  User deactivateUser(Long userId);
}
