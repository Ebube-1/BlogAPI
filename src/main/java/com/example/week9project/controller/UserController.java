package com.example.week9project.controller;

import com.example.week9project.dto.LoginDto;
import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.User;
import com.example.week9project.exception.ApiRequestException;
import com.example.week9project.repository.UserRepo;
import com.example.week9project.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final UserServices userServices;
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserServices userServices, UserRepo userRepo) {
        this.userServices = userServices;
        this.userRepo = userRepo;
    }


    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDto registrationDto){
        return new ResponseEntity<>(userServices.registerUser(registrationDto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        return new ResponseEntity<String>(userServices.login(loginDto), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/getUsers")
    public List<User> users(){
        return userServices.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getAUser(@PathVariable Long userId){
        return userServices.getUser(userId);
    }

}
