package com.yogesh.todo.controller;


import com.yogesh.todo.dto.JwtAuthResponse;
import com.yogesh.todo.dto.LoginDto;
import com.yogesh.todo.dto.RegisterDto;
import com.yogesh.todo.service.AuthService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    // Build Register REST API
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        logger.info("Registering new user: {}",registerDto.getUsername()+"->"+registerDto.getEmail());
        String response = authService.register(registerDto);
        logger.info("User {} registered successfully",registerDto.getUsername()+"->"+registerDto.getEmail());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        logger.info("User {} attempting to log in",loginDto.getUsernameOrEmail());
        String token = authService.login(loginDto);

        logger.info("User {} logged in successfully, JWT token generated",loginDto.getUsernameOrEmail());

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
    }

}
