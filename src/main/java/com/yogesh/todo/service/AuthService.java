package com.yogesh.todo.service;

import com.yogesh.todo.dto.LoginDto;
import com.yogesh.todo.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);
}
