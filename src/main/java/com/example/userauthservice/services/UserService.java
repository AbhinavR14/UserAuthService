package com.example.userauthservice.services;

import com.example.userauthservice.models.User;
import com.example.userauthservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepo userRepo;

  public User getUserDetails(long id) {
    Optional<User> userOptional = userRepo.findById(id);

    if (userOptional.isPresent())
      return userOptional.get();

    return null;
  }
}
