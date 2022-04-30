package com.example.week9project.service.serviceImpl;

import com.example.week9project.Role;
import com.example.week9project.dto.LoginDto;
import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.User;
import com.example.week9project.exception.ApiRequestException;
import com.example.week9project.repository.UserRepo;
import com.example.week9project.service.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServicesImplTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServicesImpl underTest;

    @Mock
    private ModelMapper mapper;

    private RegistrationDto registrationDto;

    private User user;


    @BeforeEach
    void setUp() {
        registrationDto = new RegistrationDto();
        registrationDto.setUsername("ebube");
        registrationDto.setEmail("ebube@gmail.com");
        registrationDto.setPassword("1234");

        user = new User();
        user.setUserId(1l);
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword());
    }

    @Test
    void shouldGetAllUsers() {
        List<User> userList = List.of(new User(1L, "ebube", "ebube@gmail.com","1234", Role.USER));
        when(userRepo.findAll()).thenReturn(userList);
        ResponseEntity<List<RegistrationDto>> response = underTest.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        //assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).size()).isEqualTo(userList.size());

    }


    @Test
    void shouldGetAUser() {
        when(userRepo.getById(user.getUserId())).thenReturn(user);
        when(mapper.map(user, RegistrationDto.class)).thenReturn(registrationDto);
        ResponseEntity<RegistrationDto> user1 = underTest.getUser(user.getUserId());

        assertThat(user1.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(user1.getBody()).isNotNull();
        assertThat(user1.getBody().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldRegisterUser() {
        when(userRepo.findByUsername(registrationDto.getUsername())).thenReturn(null);

        when(mapper.map(registrationDto, User.class)).thenReturn(user);
        when(mapper.map(user, RegistrationDto.class)).thenReturn(registrationDto);

        ResponseEntity<RegistrationDto> response = underTest.registerUser(registrationDto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists(){
        registrationDto = new RegistrationDto();
        registrationDto.setUsername("ebube");
        registrationDto.setEmail("ebube@gmail.com");
        registrationDto.setPassword("1234");

        given(userRepo.findByUsername(registrationDto.getUsername())).willReturn(new User());
        assertThatThrownBy(() -> underTest.registerUser(registrationDto))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("User already exists!");

    }

    @Test
    void userShouldBeAbleToLogin() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("ebube");
        loginDto.setPassword("1234");
        when(userRepo.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword())).thenReturn(user);
        when(mapper.map(user, LoginDto.class)).thenReturn(loginDto);

        ResponseEntity<LoginDto> response = underTest.login(loginDto);
        assertThat(response.getBody().getUsername()).isEqualTo(loginDto.getUsername());
        assertThat(response.getBody().getPassword()).isEqualTo(loginDto.getPassword());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldThrowExceptionWhenUsernameAndPasswordDoesNotExist() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("ebube");
        loginDto.setPassword("12344");

        when(userRepo.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword())).thenReturn(null);
        assertThatThrownBy(() -> {
            underTest.login(loginDto);
        })
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("User does not exist, kindly register");
    }


    @Test
    void shouldBeAbleToEditUserDetails() {
        when(userRepo.findById(user.getUserId())).thenReturn(Optional.ofNullable(user));
        when(mapper.map(registrationDto, User.class)).thenReturn(user);
        when(mapper.map(user, RegistrationDto.class)).thenReturn(registrationDto);
        ResponseEntity<RegistrationDto> response = underTest.editUser(registrationDto, user.getUserId());
        assertThat(Objects.requireNonNull(response.getBody()).getUsername()).isEqualTo(registrationDto.getUsername());

    }

    @Test
    void deleteUser() {
        ResponseEntity<String> response = underTest.deleteUser(user.getUserId());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo("Deleted Successfully");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}