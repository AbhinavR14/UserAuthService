package com.example.userauthservice.controllers;

import com.example.userauthservice.dtos.*;
import com.example.userauthservice.exceptions.InvalidCredentialsException;
import com.example.userauthservice.models.User;
import com.example.userauthservice.models.UserSession;
import com.example.userauthservice.services.IAuthService;
import com.example.userauthservice.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private IAuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
    User user = authService.signup(signupRequestDto.getName(),
                                    signupRequestDto.getEmail(),
                                    signupRequestDto.getPassword(),
                                    signupRequestDto.getPhoneNumber());

    return new ResponseEntity<>(ObjectMapper.from(user), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws InvalidCredentialsException {
    UserSession userSession = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    User user = userSession.getUser();
    String token = userSession.getToken();

    MultiValueMap<String, String> headers =  new LinkedMultiValueMap<>();
    headers.add(HttpHeaders.SET_COOKIE, token);
    headers.add("Generated-By", "Abhinav");

    return new ResponseEntity<>(ObjectMapper.from(user), headers, HttpStatus.OK);
  }

}
