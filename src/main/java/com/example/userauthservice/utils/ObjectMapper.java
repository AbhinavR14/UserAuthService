package com.example.userauthservice.utils;

import com.example.userauthservice.dtos.RoleDto;
import com.example.userauthservice.dtos.UserDto;
import com.example.userauthservice.models.Role;
import com.example.userauthservice.models.User;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {
  public static UserDto from(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setEmail(user.getEmail());
    userDto.setName(user.getName());
    userDto.setStatus(user.getStatus());

    List<RoleDto> roleDtos = new ArrayList<>();
    for (Role role : user.getRoles()) {
      RoleDto roleDto = new RoleDto();
      roleDto.setId(role.getId());
      roleDto.setRoleName(role.getRoleName());
      roleDto.setStatus(role.getStatus());
      roleDtos.add(roleDto);
    }
    userDto.setRoles(roleDtos);

    return userDto;
  }

  public static RoleDto from(Role role) {
    RoleDto roleDto = new RoleDto();
    roleDto.setId(role.getId());
    roleDto.setRoleName(role.getRoleName());
    roleDto.setStatus(role.getStatus());
    return roleDto;
  }
}
