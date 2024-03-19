package com.userservice.mapper;

import com.userservice.entities.role.Role;
import com.userservice.entities.user.User;
import com.userservice.dto.UserDTO;
import org.modelmapper.ModelMapper;

public class UserMapper {

  public User toEntity(UserDTO userDTO) {
    ModelMapper mapper=new ModelMapper();
    User user=mapper.map(userDTO,User.class);
    return user;
  }

  public UserDTO toDTO(User user) {
    ModelMapper mapper=new ModelMapper();
    UserDTO userDTO=mapper.map(user,UserDTO.class);
    return userDTO;
  }

}
