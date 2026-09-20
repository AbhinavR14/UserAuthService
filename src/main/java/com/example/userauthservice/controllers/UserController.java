package com.example.userauthservice.controllers;

import com.example.userauthservice.dtos.UserDto;
import com.example.userauthservice.models.User;
import com.example.userauthservice.services.UserService;
import com.example.userauthservice.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    return ObjectMapper.from(user);
  }

}
