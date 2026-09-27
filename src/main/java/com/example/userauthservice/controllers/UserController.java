package com.example.userauthservice.controllers;

import com.example.userauthservice.dtos.UserDto;
import com.example.userauthservice.models.Status;
import com.example.userauthservice.models.User;
import com.example.userauthservice.services.IUserService;
import com.example.userauthservice.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private IUserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserDetails(@PathVariable Long id) {
    User user = userService.getUserDetails(id);
    return ResponseEntity.ok(ObjectMapper.from(user));
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) Status status) {
    List<UserDto> userDtos = new ArrayList<>();

    for (User user : userService.getAllUsers(status))
      userDtos.add(ObjectMapper.from(user));

    return ResponseEntity.ok(userDtos);
  }

  @PutMapping("/{userId}/roles/{roleId}")
  public ResponseEntity<UserDto> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
    User user = userService.assignRole(userId, roleId);
    return ResponseEntity.ok(ObjectMapper.from(user));
  }

  @DeleteMapping("/{userId}/roles/{roleId}")
  public ResponseEntity<UserDto> removeRole(@PathVariable Long userId, @PathVariable Long roleId) {
    User user = userService.removeRole(userId, roleId);
    return ResponseEntity.ok(ObjectMapper.from(user));
  }

  @PutMapping("/{userId}/activate")
  public ResponseEntity<UserDto> activateUser(@PathVariable Long userId) {
    User user = userService.activateUser(userId);
    return ResponseEntity.ok(ObjectMapper.from(user));
  }

  @PutMapping("/{userId}/deactivate")
  public ResponseEntity<UserDto> deactivateUser(@PathVariable Long userId) {
    User user = userService.deactivateUser(userId);
    return ResponseEntity.ok(ObjectMapper.from(user));
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }

}
