package com.example.userauthservice.services;

import com.example.userauthservice.clients.KafkaProducerClient;
import com.example.userauthservice.dtos.EmailDto;
import com.example.userauthservice.dtos.UserSession;
import com.example.userauthservice.exceptions.InvalidCredentialsException;
import com.example.userauthservice.exceptions.PasswordMissmatchException;
import com.example.userauthservice.exceptions.UserAlreadyExistsException;
import com.example.userauthservice.exceptions.UserNotSignedUpException;
import com.example.userauthservice.models.Role;
import com.example.userauthservice.models.Status;
import com.example.userauthservice.models.User;
import com.example.userauthservice.repositories.RoleRepo;
import com.example.userauthservice.repositories.UserRepo;
import com.example.userauthservice.repositories.UserSessionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

  @Autowired
  private IRoleService roleService;

  @Autowired
  private UserRepo userRepo;

//  @Autowired
//  private RoleRepo roleRepo;

  @Autowired
  private UserSessionRepo userSessionRepo;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private SecretKey secretKey;

  @Autowired
  private KafkaProducerClient kafkaProducerClient;

  @Autowired
  private ObjectMapper objectMapper;

  private static final long EXPIRATION = 1000 * 60 * 60;     // 1 hr -> 1000 ms * 60 * 60 = 36,00,000

  @Override
  public User signup(String name, String email, String password, String phoneNumber) {
    Optional<User> userOptional = userRepo.findByEmail(email);
    if (userOptional.isPresent())
      throw new UserAlreadyExistsException("User with email " + email + " already exists.");

    User user = new User();
    user.setName(name);
    user.setEmail(email);
    user.setPassword(bCryptPasswordEncoder.encode(password));
    user.setPhoneNumber(phoneNumber);

    Role role = roleService.getRoleByName("CUSTOMER");
    user.setRoles(List.of(role));

    userRepo.save(user);

    EmailDto emailDto = new EmailDto();
    emailDto.setTo(email);
    emailDto.setFrom("abhinavrawat14@gmail.com");
    emailDto.setSubject("Welcome to Scaler");
    emailDto.setBody("Have a good learning experience");

    try {
      kafkaProducerClient.sendMessage("signup", objectMapper.writeValueAsString(emailDto));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e.getMessage());
    }

    return user;
  }

  @Override
  public UserSession login(String email, String password) throws InvalidCredentialsException {
    User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new InvalidCredentialsException(new UserNotSignedUpException("Please signup first!")));

    if (!bCryptPasswordEncoder.matches(password, user.getPassword()))
        throw new InvalidCredentialsException(new PasswordMissmatchException("Please check your password!"));

    Map<String, Object> claims = new HashMap<>();     // This is our "payload"
    claims.put("user_id", user.getId());

    List<String> roles = user.getRoles().stream()
                                        .filter(role -> role.getStatus() == Status.ACTIVE)
                                        .map(Role::getRoleName)
                                        .toList();
    claims.put("roles", roles);

    claims.put("issuer", "scaler");

    Long currentTime = System.currentTimeMillis();
    claims.put("iat", currentTime);
    claims.put("exp", currentTime + EXPIRATION);      // currentTime + 1hr

    String token = Jwts.builder().claims(claims)
            .signWith(secretKey)
            .compact();

    UserSession userSession = new UserSession();
    userSession.setUser(user);
    userSession.setToken(token);
    userSessionRepo.save(userSession);

    return userSession;
  }
}
