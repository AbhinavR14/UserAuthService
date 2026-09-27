package com.example.userauthservice.services;

import com.example.userauthservice.exceptions.InvalidRoleOperationException;
import com.example.userauthservice.exceptions.InvalidUserOperationException;
import com.example.userauthservice.exceptions.UserNotFoundException;
import com.example.userauthservice.models.Role;
import com.example.userauthservice.models.Status;
import com.example.userauthservice.models.User;
import com.example.userauthservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{

  @Autowired
  private IRoleService roleService;

  @Autowired
  private UserRepo userRepo;

  @Override
  public User getUserDetails(long userId) {
    return userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
  }

  @Override
  public List<User> getAllUsers(Status status) {
    if (status == null)
      return userRepo.findAll();

    return userRepo.findByStatus(status);
  }

  @Override
  public User assignRole(Long userId, Long roleId) {
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));

    if (user.getStatus() != Status.ACTIVE)
      throw new InvalidUserOperationException("Cannot assign role to inactive or deleted user: " + userId);

    Role role = roleService.getRoleById(roleId);

    if (role.getStatus() != Status.ACTIVE)
      throw new InvalidRoleOperationException("Cannot assign inactive or deleted role: " + roleId);

    if (user.getRoles().contains(role))
      throw new InvalidRoleOperationException("User already has this role assigned: " + roleId);

    user.getRoles().add(role);

    return userRepo.save(user);
  }

  @Override
  public User removeRole(Long userId, Long roleId) {
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));

    Role role = roleService.getRoleById(roleId);

    if (!user.getRoles().contains(role))
      throw new InvalidRoleOperationException("User does not have this role assigned: " + roleId);

    user.getRoles().remove(role);

    return userRepo.save(user);
  }

  @Override
  @Transactional
  public void deleteUser(Long userId) {
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));

    if (user.getStatus() == Status.DELETED)
      throw new InvalidUserOperationException("User is already deleted: " +  userId);

    user.setStatus(Status.DELETED);
    userRepo.save(user);
  }

  @Override
  public User activateUser(Long userId) {
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));

    if (user.getStatus() == Status.DELETED)
      throw new InvalidUserOperationException("Deleted user cannot be activated: " + userId);

    user.setStatus(Status.ACTIVE);

    return userRepo.save(user);
  }

  @Override
  public User deactivateUser(Long userId) {
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));

    if (user.getStatus() == Status.DELETED)
      throw new InvalidUserOperationException("Deleted user cannot be deactivated: " + userId);

    user.setStatus(Status.INACTIVE);

    return userRepo.save(user);
  }

}
