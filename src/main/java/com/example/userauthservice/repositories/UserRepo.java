package com.example.userauthservice.repositories;

import com.example.userauthservice.models.Status;
import com.example.userauthservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByIdAndStatus(Long id, Status status);

  List<User> findByStatus(Status status);
}
