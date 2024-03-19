package com.userservice.dto;

import com.userservice.entities.user.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link User}
 */
@Data
public class UserDTO implements Serializable {

  private long id;
  private String firstName;
  private String lastName;
  private String emailId;
  private String mobileNumber;
  private String password;
  private LocalDateTime registrationDatetime;
  private RoleDTO roleDTO;
}
