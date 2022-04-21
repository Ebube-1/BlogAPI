package com.example.week9project.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 100, message = "Text must be at most 100 characters long")
    private String text;
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;
    private int likes;
}
