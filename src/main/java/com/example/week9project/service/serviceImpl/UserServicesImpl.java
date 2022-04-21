package com.example.week9project.service.serviceImpl;

import com.example.week9project.dto.LoginDto;
import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.User;
import com.example.week9project.exception.ApiRequestException;
import com.example.week9project.repository.UserRepo;
import com.example.week9project.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImpl implements UserServices {

    private final UserRepo userRepo;

    @Autowired
    public UserServicesImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User getUser(Long userId) {
        return userRepo.findById(userId).get();
    }

    @Override
    public String registerUser(RegistrationDto registrationDto) {
        User newUser = new User();
        User existingUser = userRepo.findByUsername(registrationDto.getUsername());
        if (existingUser == null) {
            newUser.setUsername(registrationDto.getUsername());
            newUser.setEmail(registrationDto.getEmail());
            newUser.setPassword(registrationDto.getPassword());
            newUser.setConfirmationPassword(registrationDto.getPasswordConfirmation());
            userRepo.save(newUser);
        } else {
            throw new ApiRequestException("User with " + existingUser.getUsername() + " username already exists!");
        }
            return "User successfully registered";
    }

    @Override
    public String login(LoginDto loginDto) {
        User user = userRepo.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        if(user == null ){
            throw new ApiRequestException("User does not exist, kindly register");
        }
        return "Login Successful";
    }



}
