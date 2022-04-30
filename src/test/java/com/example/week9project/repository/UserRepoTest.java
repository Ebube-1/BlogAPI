package com.example.week9project.repository;

import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepoTest {


    @Autowired
    UserRepo userRepo;

    @Test
    void findByUsername() {
        User user = new User();
        user.setUsername("chisom");
        user.setEmail("chisom@gmail.com");
        user.setPassword("1234");
        userRepo.save(user);

        User foundUser = userRepo.findByUsername("chisom");

        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void findByUsernameAndPassword() {
        User user = new User();
        user.setUsername("cecelia");
        user.setEmail("chisom@gmail.com");
        user.setPassword("1234");
        userRepo.save(user);

        User founduser = userRepo.findByUsernameAndPassword("cecelia","1234");
        assertThat(founduser).isEqualTo(user);
    }
}