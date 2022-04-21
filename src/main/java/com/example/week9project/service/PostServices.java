package com.example.week9project.service;

import com.example.week9project.dto.PostDto;
import com.example.week9project.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostServices {
    void deletePost(Long postId);
    List<Post> getAllPosts();
    void insert(Post post);
    Post createNewPost(Long userId,PostDto postDto);
    List<Post> findPostsByUserId(Long userId);
}
