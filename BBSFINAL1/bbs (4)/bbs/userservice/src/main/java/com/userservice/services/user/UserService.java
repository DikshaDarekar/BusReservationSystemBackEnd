package com.userservice.services.user;


import com.userservice.entities.user.User;
import com.userservice.exceptions.CustomException;
import com.userservice.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  /**
   * To save user other than customer
   * @param userDTO
   * @throws CustomException
   */
  void save(UserDTO userDTO) throws CustomException;

  /**
   * To find user by email id
   * @param emailId
   * @return {@link UserDTO}
   */
  UserDTO findByEmailId(String emailId);

  /**
   * To find user by id
   * @param id
   * @return {@link UserDTO}
   */
  UserDTO findById(long id);


  /**
   * To find user by mobile number
   * @param mobileNumber
   * @return {@link UserDTO}
   */
  UserDTO findByMobileNumber(String mobileNumber);

 Page<User> findAll(Pageable pageable);
}
