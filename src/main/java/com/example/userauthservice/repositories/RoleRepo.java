package com.example.userauthservice.repositories;

import com.example.userauthservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
  Optional<Role> findByRoleName(String roleName);
}
