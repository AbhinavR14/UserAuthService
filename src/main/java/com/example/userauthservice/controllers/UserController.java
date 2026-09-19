package com.example.userauthservice.controllers;

import com.example.userauthservice.dtos.RoleDto;
import com.example.userauthservice.dtos.UserDto;
import com.example.userauthservice.models.Role;
import com.example.userauthservice.models.User;
import com.example.userauthservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public UserDto getUserDetails(@PathVariable long id) {
    User user = userService.getUserDetails(id);
    return from(user);
  }

  private UserDto from(User user) {
    UserDto userDto = new UserDto();
    userDto.setEmail(user.getEmail());
    userDto.setName(user.getName());
    userDto.setId(user.getId());

    List<RoleDto> roleDtos = new ArrayList<>();
    for (Role role : user.getRoles()) {
      RoleDto roleDto = new RoleDto();
      roleDto.setRoleName(role.getRoleName());
      roleDtos.add(roleDto);
    }
    userDto.setRoles(roleDtos);

    return userDto;
  }
}
