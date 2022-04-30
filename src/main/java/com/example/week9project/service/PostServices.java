package com.example.week9project.service;

import com.example.week9project.dto.PostDto;
import com.example.week9project.entity.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface PostServices {
    ResponseEntity<String> deletePost(Long postId);
    ResponseEntity<List<PostDto>> getAllPosts();
    ResponseEntity<PostDto> createNewPost(Long userId, Long categoryId, PostDto postDto);
    ResponseEntity<PostDto> editPost(Long postId, Long userId, PostDto postDto);
    ResponseEntity<PostDto> findPostById(long postId);
    ResponseEntity<String> likePost(Long userId, Long postId);

    ResponseEntity<String> unlikePost(Long userId, Long postId);
}
