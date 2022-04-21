package com.example.week9project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "blogUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    @Size(min = 5, message = "Username must be at least 5 characters long")
    @Column(unique = true, nullable = false)
    private String username;
    @Size(min = 5, message = "Password must be at least 5 characters long")
    @Column(unique = true, nullable = false)
    private String password;
    @Size(min = 5, message = "Should match password field")
    private String confirmationPassword;
    @OneToMany
//    (fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<Role> roles;
}
