package com.example.week9project.dto;

import com.example.week9project.entity.Comment;
import com.example.week9project.entity.Post;
import com.example.week9project.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LikeDto {
    private User user;
    private Post post;
    private Comment comment;
}
