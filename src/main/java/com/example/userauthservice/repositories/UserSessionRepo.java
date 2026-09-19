package com.example.userauthservice.repositories;

import com.example.userauthservice.dtos.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepo extends JpaRepository<UserSession, Long> {
}
