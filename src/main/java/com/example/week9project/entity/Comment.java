package com.example.week9project.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 50, message = "Text must be at most 50 characters long")
    private String text;
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;
    private long noOfLikes;
    private LocalDateTime timePosted;

}


