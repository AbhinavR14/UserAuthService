package com.example.userauthservice.dtos;

import com.example.userauthservice.models.BaseModel;
import com.example.userauthservice.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserSession extends BaseModel {

  @ManyToOne
  private User user;

  private String token;
}
