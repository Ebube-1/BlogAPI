package com.example.week9project.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String title;
    private String body;
    private long noOfLikes;
    private LocalDateTime datePosted;
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;
//    @OneToMany
//    private List<Comment> comment;
    private long noOfComments;


}
