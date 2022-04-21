package com.example.week9project.service;

import com.example.week9project.dto.PostDto;
import com.example.week9project.entity.Comment;
import com.example.week9project.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentServices {
    boolean deleteComment(Long id);
    List<Comment> getAllCommentsInAPost(long postId);
    Comment createNewComment(long userId, long postId, PostDto postDto);
}
