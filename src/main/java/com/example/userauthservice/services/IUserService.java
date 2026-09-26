package com.example.userauthservice.services;

import com.example.userauthservice.models.User;

public interface IUserService {
  User getUserDetails(long id);
  User assignRole(Long userId, Long roleId);
  User removeRole(Long userId, Long roleId);
}
