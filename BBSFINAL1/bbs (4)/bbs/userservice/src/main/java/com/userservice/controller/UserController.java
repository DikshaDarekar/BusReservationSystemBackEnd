package com.userservice.controller;

import com.userservice.entities.user.User;
import com.userservice.exceptions.CustomException;
import com.userservice.services.user.UserService;
import com.userservice.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * To save user
     *
     * @param userDTO
     * @return {@link ResponseEntity <  ActionResponse  >}
     */
    @PostMapping("/users")
    public ResponseEntity<?> save(@RequestBody UserDTO userDTO) {
        log.info("Rest request to save user");
        try {
            userService.save(userDTO);
            Map<String,Object> responseMap=new HashMap<>();
            responseMap.put("message","User saved successfully");
            return ResponseEntity.ok().body(responseMap);
        } catch (CustomException customException) {
            if (customException.getExceptions() != null && !customException.getExceptions().isEmpty())
                return ResponseEntity.badRequest().body(customException.getExceptions());
            else return ResponseEntity.badRequest().body(customException.getException());
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/users/findall")
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            Page<User> page = userService.findAll(pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/users")
    ResponseEntity<?> findByEmailId(@PathVariable(name = "emailId") String emailId) {
        log.info("Rest request to authenticate user");
        try {
            UserDTO userDTO1 = userService.findByEmailId(emailId);
            return ResponseEntity.ok().body(userDTO1);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/users/findById/{id}")
    ResponseEntity<?> findById(@PathVariable(name = "id") long id) {
        log.info("Rest request to authenticate user");
        try {
            UserDTO userDTO1 = userService.findById(id);
            return ResponseEntity.ok().body(userDTO1);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

}