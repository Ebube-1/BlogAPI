package com.example.week9project.service;

import com.example.week9project.dto.LoginDto;
import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserServices {
  ResponseEntity<List<RegistrationDto>> getAllUsers();
  //  User getUser(String username);
  ResponseEntity<RegistrationDto> getUser(Long userId);

  ResponseEntity<RegistrationDto> registerUser(RegistrationDto registrationDto);

  ResponseEntity<LoginDto> login(LoginDto loginDto);

  ResponseEntity<RegistrationDto> editUser(RegistrationDto registrationDto, Long userId);

  ResponseEntity<String>deleteUser(long userId);
}
