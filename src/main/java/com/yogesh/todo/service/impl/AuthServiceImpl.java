package com.yogesh.todo.service.impl;

import com.yogesh.todo.dto.LoginDto;
import com.yogesh.todo.dto.RegisterDto;
import com.yogesh.todo.entity.Role;
import com.yogesh.todo.entity.User;
import com.yogesh.todo.exception.TodoAPIException;
import com.yogesh.todo.repository.RoleRepository;
import com.yogesh.todo.repository.UserRepository;
import com.yogesh.todo.security.JwtTokenProvider;
import com.yogesh.todo.service.AuthService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {
        logger.info("Registering new user: {} ", registerDto.getUsername());

        // check username is already exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            logger.warn("Registration failed: Username '{}' already exists",registerDto.getUsername());
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Username already exists!");
        }
        // check email is already exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            logger.warn("Registration failed: Email '{}' already exists",registerDto.getEmail());
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Email already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        logger.info("User '{}' registered successfully!", registerDto.getUsername());

        return "User Registered Successfully!";
    }

    @Override
    public String login(LoginDto loginDto) {
        logger.info("Getting login request for user: {}",loginDto.getUsernameOrEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        logger.info("User '{}' logged in successfully!",loginDto.getUsernameOrEmail());
        return token;
    }
}
