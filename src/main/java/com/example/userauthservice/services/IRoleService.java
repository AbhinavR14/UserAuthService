package com.example.userauthservice.services;

import com.example.userauthservice.models.Role;

import java.util.List;

public interface IRoleService {
  Role createRole(String roleName);

  Role getRoleByName(String roleName);
  List<Role> getAllRoles();

  Role deactivateRole(Long roleId);
  Role activateRole(Long roleId);

  Role deleteRole(Long roleId);

  Role getRoleById(Long roleId);
}
