package com.example.userauthservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping
  public ResponseEntity<String> testAuthentication(Authentication authentication) {
    return ResponseEntity.ok(
            "Authenticated user: " + authentication.getPrincipal()
                    + ", Authorities: " + authentication.getAuthorities());
  }
}
