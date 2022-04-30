package com.example.week9project.controller;

import com.example.week9project.dto.LoginDto;
import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.User;
import com.example.week9project.repository.UserRepo;
import com.example.week9project.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserServices userServices;
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserServices userServices, UserRepo userRepo) {
        this.userServices = userServices;
        this.userRepo = userRepo;
    }


    @PostMapping(value = "/register")
    public ResponseEntity<RegistrationDto> register(@RequestBody RegistrationDto registrationDto){
        return userServices.registerUser(registrationDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginDto> login(@RequestBody LoginDto loginDto){
        return userServices.login(loginDto);
    }

    @PutMapping(path = "/edit/{userId}")
    public ResponseEntity<RegistrationDto> editUser(@RequestBody RegistrationDto registrationDto, @PathVariable("userId") Long userId){
        return userServices.editUser(registrationDto, userId);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") long userId){
        return userServices.deleteUser(userId);
    }

    @GetMapping
    public ResponseEntity<List<RegistrationDto>> users(){
        return userServices.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RegistrationDto> getAUser(@PathVariable Long userId){
        return userServices.getUser(userId);
    }


}
