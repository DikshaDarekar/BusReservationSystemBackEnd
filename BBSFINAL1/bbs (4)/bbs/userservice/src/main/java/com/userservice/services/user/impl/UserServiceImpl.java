package com.userservice.services.user.impl;

import com.userservice.entities.role.Role;
import com.userservice.entities.user.User;
import com.userservice.exceptions.CustomException;
import com.userservice.repositories.user.UserRepository;
import com.userservice.services.user.UserService;
import com.userservice.dto.UserDTO;
import com.userservice.mapper.UserMapper;
import com.userservice.utils.StringUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


/**
 * Service class for {@link User}
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * To save user other than customer
     *
     * @param userDTO
     * @throws CustomException
     */
    public void save(UserDTO userDTO) throws CustomException {
        CustomException customException = validateUser(userDTO);
        if (customException == null) {
            customException = new CustomException();
        }

        if (findByEmailId(userDTO.getEmailId()) != null) {
            customException.addException("emailId", "Email already exists");
        }
        if (findByMobileNumber(userDTO.getMobileNumber()) != null) {
            customException.addException("mobileNumber", "Mobile number already exists");
        }

        if (customException.getExceptions().size() > 0) throw customException;

        UserMapper mapper = new UserMapper();
        User user = mapper.toEntity(userDTO);
        user.setId(0);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRegistrationDatetime(LocalDateTime.now());
        Role role=new Role();
        role.setId(userDTO.getRoleDTO().getId());
        user.setRole(role);
        userRepository.save(user);
    }


    /**
     * To find user by email id
     *
     * @param emailId
     * @return {@link UserDTO}
     */
    public UserDTO findByEmailId(String emailId) {
        if (StringUtility.isNullEmptyOrBlank(emailId)) return null;
        Optional<User> userOptional = userRepository.findByEmailId(emailId);
        if (userOptional.isPresent()) {
            UserMapper mapper = new UserMapper();
            return mapper.toDTO(userOptional.get());
        }
        return null;
    }

    /**
     * To find user by id
     *
     * @param id
     * @return {@link UserDTO}
     */
    public UserDTO findById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserMapper mapper = new UserMapper();
            return mapper.toDTO(userOptional.get());
        }
        return null;
    }

    /**
     * To find user by mobile number
     *
     * @param mobileNumber
     * @return {@link UserDTO}
     */
    public UserDTO findByMobileNumber(String mobileNumber) {
        if (StringUtility.isNullEmptyOrBlank(mobileNumber)) return null;
        Optional<User> userOptional = userRepository.findByMobileNumber(mobileNumber);
        if (userOptional.isPresent()) {
            UserMapper mapper = new UserMapper();
            return mapper.toDTO(userOptional.get());
        }
        return null;
    }

    /**
     * To authenticate user based on email-id and password
     *
     * @param userDTO
     * @return {@link UserDTO}
     * @throws CustomException
     */
    public UserDTO authenticate(UserDTO userDTO) throws CustomException {
        CustomException customException = new CustomException();
        if (userDTO == null) {
            customException.addException("emailId", "Email-Id is required");
            customException.addException("password", "Password is required");
        } else {
            if (StringUtility.isNullEmptyOrBlank(userDTO.getEmailId())) {
                customException.addException("emailId", "Email-Id is required");
            }
            if (StringUtility.isNullEmptyOrBlank(userDTO.getPassword())) {
                customException.addException("password", "Password is required");
            }
        }

        if (customException.getExceptions().size() > 0) throw customException;

        Optional<User> userOptional = userRepository.findByEmailId(userDTO.getEmailId());
        if (userOptional.isPresent()) {
            UserMapper mapper = new UserMapper();
            UserDTO userDTO1 = mapper.toDTO(userOptional.get());
            return userDTO1;
        }
        return null;
    }

    public Page<User> findAll(Pageable pageable) {
        Page<User> page = this.userRepository.findAll(pageable);
        return page;
    }


    /**
     * To validate user
     *
     * @param userDTO
     * @return {@link CustomException}
     */
    private CustomException validateUser(UserDTO userDTO) throws CustomException {
        CustomException customException = new CustomException();
        if (StringUtility.isNullEmptyOrBlank(userDTO.getFirstName())) {
            customException.addException("firstName", "First name is required");
        }
        if (StringUtility.isNullEmptyOrBlank(userDTO.getLastName())) {
            customException.addException("lastName", "Last name is required");
        }
        if (StringUtility.isNullEmptyOrBlank(userDTO.getEmailId())) {
            customException.addException("emailId", "Email-Id is required");
        }
        if (StringUtility.isNullEmptyOrBlank(userDTO.getMobileNumber())) {
            customException.addException("mobileNumber", "Mobile number is required");
        }
        if (StringUtility.isNullEmptyOrBlank(userDTO.getPassword())) {
            customException.addException("password", "Password is required");
        }
        if (customException.getExceptions().size() > 0) return customException;
        return null;
    }

}
