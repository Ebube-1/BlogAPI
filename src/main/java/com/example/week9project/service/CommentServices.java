package com.example.week9project.service;

import com.example.week9project.dto.CommentDto;
import com.example.week9project.dto.PostDto;
import com.example.week9project.entity.Comment;
import com.example.week9project.entity.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommentServices {

    ResponseEntity<String> deleteComment(long id, long userId);

//    List<Comment> getAllCommentsInAPost(long postId);
    ResponseEntity<CommentDto> createNewComment(long userId, long postId, CommentDto commentDto);
    ResponseEntity<CommentDto> editComment(long id, long userId, CommentDto commentDto);

    ResponseEntity<String> likeComment(Long userId, Long postId, Long id);

    ResponseEntity<String> unlikeComment(Long id, Long postId, Long userId);
}
