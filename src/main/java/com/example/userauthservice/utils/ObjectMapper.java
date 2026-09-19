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

//  public static Role from(RoleDto roleDto) {
//    return new Role()
//  }
}
