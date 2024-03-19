package com.apigateway.api.rest.auth;

import com.apigateway.dto.LoginDto;
import com.apigateway.entities.user.User;
import com.apigateway.security.JWTUtil;
import com.apigateway.services.user.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto){
        try {
            log.info("Login request");
            User user=userDetailsService.loadUserByUsername(loginDto.getUsernameOrEmail());
            String token=jwtUtil.generateToken(user);
            Map<String,Object> response=new HashMap<>();
            response.put("access_token",token);
            response.put("user",user);
            return ResponseEntity.ok().body(response);
        }catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server err");
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Test");
    }
}