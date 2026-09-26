package com.example.userauthservice.services;

import com.example.userauthservice.exceptions.InvalidRoleOperationException;
import com.example.userauthservice.exceptions.RoleAlreadyExistsException;
import com.example.userauthservice.exceptions.RoleNotFoundException;
import com.example.userauthservice.models.Role;
import com.example.userauthservice.models.Status;
import com.example.userauthservice.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

  @Autowired
  private RoleRepo roleRepo;

  @Override
  public Role createRole(String roleName) {
    Optional<Role> existingRoleOptional = roleRepo.findByRoleName(roleName);
    if (existingRoleOptional.isPresent())
      throw new RoleAlreadyExistsException("Role already exists: " + roleName);

    Role role = new Role();
    role.setRoleName(roleName);

    return roleRepo.save(role);
  }

  @Override
  public Role getRoleById(Long roleId) {
    return roleRepo.findById(roleId)
            .orElseThrow(() -> new RoleNotFoundException("Role with id " + roleId + " not found"));
  }

  @Override
  public Role getRoleByName(String roleName) {
    return roleRepo.findByRoleNameAndStatus(roleName, Status.ACTIVE)
            .orElseThrow(() -> new RoleNotFoundException("Active role not found: " + roleName));
  }

  @Override
  public List<Role> getAllRoles() {
    return roleRepo.findAll();
  }

  @Override
  public Role activateRole(Long roleId) {
    Role role = roleRepo.findById(roleId)
            .orElseThrow(() -> new RoleNotFoundException("Role with id " + roleId + " not found"));

    if (role.getStatus() == Status.DELETED)
      throw new InvalidRoleOperationException("Deleted role cannot be activated: " + roleId);

    role.setStatus(Status.ACTIVE);
    return roleRepo.save(role);
  }

  @Override
  public Role deactivateRole(Long roleId) {
    Role role = roleRepo.findById(roleId)
            .orElseThrow(() -> new RoleNotFoundException("Role with id " + roleId + " not found"));

    if (role.getStatus() == Status.DELETED)
      throw new InvalidRoleOperationException("Deleted role cannot be deactivated: " + roleId);

    role.setStatus(Status.INACTIVE);
    return roleRepo.save(role);
  }

  @Override
  public Role deleteRole(Long roleId) {
    Role role = roleRepo.findById(roleId)
            .orElseThrow(() -> new RoleNotFoundException("Role with id " + roleId + " not found"));

    if (role.getStatus() == Status.DELETED)
      throw new RoleNotFoundException("Role is already deleted: " + roleId);

    role.setStatus(Status.DELETED);

    return roleRepo.save(role);
  }

}
