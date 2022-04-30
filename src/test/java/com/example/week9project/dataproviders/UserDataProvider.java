package com.example.week9project.dataproviders;

import com.example.week9project.entity.User;
import com.example.week9project.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataProvider {

    private final UserRepo userRepo;

    public User save() {
        User user = new User();
        user.setUsername("Ebube");
        user.setEmail("ebube@gmail.com");
        user.setPassword("ebube");
        return userRepo.save(user);
    }
}
