package com.example.week9project.service;

import com.example.week9project.dto.LoginDto;
import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServices {
    List<User> getAllUsers();
    User getUser(String username);
    User getUser(Long userId);
    String registerUser(RegistrationDto registrationDto);
    String login(LoginDto loginDto);
}
