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

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {
        // check username is already exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Username already exists!");
        }
        // check email is already exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
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

        return "User Registered Successfully!";
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }
}
