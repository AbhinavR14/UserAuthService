package com.example.userauthservice.controllers;

import com.example.userauthservice.dtos.CreateRoleRequestDto;
import com.example.userauthservice.dtos.RoleDto;
import com.example.userauthservice.models.Role;
import com.example.userauthservice.services.IRoleService;
import com.example.userauthservice.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

  @Autowired
  private IRoleService roleService;

  @PostMapping
  public ResponseEntity<RoleDto> createRole(@RequestBody CreateRoleRequestDto createRoleRequestDto) {
    Role role = roleService.createRole(createRoleRequestDto.getRoleName());

    return new ResponseEntity<>(ObjectMapper.from(role), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<RoleDto>> getAllRoles() {
    List<RoleDto> roleDtos = roleService.getAllRoles().stream()
                                                      .map(ObjectMapper::from)
                                                      .toList();

    return ResponseEntity.ok(roleDtos);
  }

  @PutMapping("/{roleId}/activate")
  public ResponseEntity<RoleDto> activateRole(@PathVariable("roleId") Long roleId) {
    Role role = roleService.activateRole(roleId);
    return ResponseEntity.ok(ObjectMapper.from(role));
  }

  @PutMapping("/{roleId}/deactivate")
  public ResponseEntity<RoleDto> deactivateRole(@PathVariable("roleId") Long roleId) {
    Role role = roleService.deactivateRole(roleId);
    return ResponseEntity.ok(ObjectMapper.from(role));
  }

  @DeleteMapping("/{roleId}")
  public ResponseEntity<RoleDto> deleteRole(@PathVariable("roleId") Long roleId) {
    Role role = roleService.deleteRole(roleId);

    return ResponseEntity.ok(ObjectMapper.from(role));
  }

}
