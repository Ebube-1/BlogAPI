package com.example.week9project.service.serviceImpl;

import com.example.week9project.Role;
import com.example.week9project.dto.LoginDto;
import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.User;
import com.example.week9project.exception.ApiRequestException;
import com.example.week9project.repository.UserRepo;
import com.example.week9project.service.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServicesImpl implements UserServices {

    private final UserRepo userRepo;
    private final ModelMapper mapper;

    @Autowired
    public UserServicesImpl(UserRepo userRepo, ModelMapper mapper) {

        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<List<RegistrationDto>> getAllUsers() {
        List<RegistrationDto> newList = new ArrayList<>();
        List<User> users = userRepo.findAll();
        for(User user: users){
            newList.add(mapper.map( user, RegistrationDto.class));
        }
        return new ResponseEntity<>(newList, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<RegistrationDto> getUser(Long userId) {
        User user = userRepo.getById(userId);
        return new ResponseEntity<>(mapper.map(user, RegistrationDto.class), HttpStatus.FOUND);
    }


    @Override
    public ResponseEntity<RegistrationDto> registerUser(RegistrationDto registrationDto) {
        User existingUser = userRepo.findByUsername(registrationDto.getUsername());
        User newUser;
        if (existingUser == null) {
            newUser = mapper.map(registrationDto, User.class );
            newUser.setRole(Role.USER);
            userRepo.save(newUser);
        } else {
            throw new ApiRequestException("User already exists!");
        }
        return new ResponseEntity<>(mapper.map(newUser, RegistrationDto.class), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoginDto> login(LoginDto loginDto) {
        User user = userRepo.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        if(user == null ){
            throw new ApiRequestException("User does not exist, kindly register");
        }
        return new ResponseEntity<>(mapper.map(user, LoginDto.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RegistrationDto> editUser(RegistrationDto registrationDto, Long userId){
        User user = userRepo.findById(userId).orElseThrow(() -> new ApiRequestException("User does not exist"));
            mapper.map(registrationDto, User.class);
            userRepo.save(user);
        return new ResponseEntity<>(mapper.map(user, RegistrationDto.class), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteUser(long userId){
        userRepo.deleteById(userId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }



}
