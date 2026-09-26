package com.example.userauthservice.configs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {

  @Bean
  public SecretKey secretKey() {
    MacAlgorithm algorithm = Jwts.SIG.HS256;
    return algorithm.key().build();
  }
}
