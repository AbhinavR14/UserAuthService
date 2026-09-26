package com.example.userauthservice.services;

import com.example.userauthservice.exceptions.InvalidRoleOperationException;
import com.example.userauthservice.exceptions.UserNotFoundException;
import com.example.userauthservice.models.Role;
import com.example.userauthservice.models.Status;
import com.example.userauthservice.models.User;
import com.example.userauthservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{

  @Autowired
  private IRoleService roleService;

  @Autowired
  private UserRepo userRepo;

  @Override
  public User getUserDetails(long id) {
    Optional<User> userOptional = userRepo.findById(id);

    if (userOptional.isPresent())
      return userOptional.get();

    return null;
  }

  @Override
  public User assignRole(Long userId, Long roleId) {
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));

    Role role = roleService.getRoleById(roleId);

    if (role.getStatus() != Status.ACTIVE)
      throw new InvalidRoleOperationException("Cannot assign inactive or deleted role: " + roleId);

    if (user.getRoles().contains(role))
      throw new InvalidRoleOperationException("User already has this role assigned: " + roleId);

    user.getRoles().add(role);

    return userRepo.save(user);
  }
}
