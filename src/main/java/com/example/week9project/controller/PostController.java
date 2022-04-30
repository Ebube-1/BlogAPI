package com.example.week9project.controller;

import com.example.week9project.dto.PostDto;
import com.example.week9project.repository.PostRepo;
import com.example.week9project.service.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostRepo postRepo;
    private final PostServices postServices;

    @Autowired
    public PostController(PostRepo postRepo, PostServices postServices) {
        this.postRepo = postRepo;
        this.postServices = postServices;
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deletePost (@PathVariable("id") Long postId){
       return postServices.deletePost(postId);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return postServices.getAllPosts();
    }

    @PostMapping(value = "/create/{userId}/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Long userId, @PathVariable Long categoryId){
        return postServices.createNewPost(userId, categoryId, postDto);
    }

    @PutMapping(path = "/{postId}/{userId}")
    public ResponseEntity<PostDto> editPost(@RequestBody PostDto postDto, @PathVariable("postId") Long postId, @PathVariable("userId") Long userId){
        return postServices.editPost(postId, userId, postDto);
    }

    @PostMapping(value = "likes/{userId}/{postId}")
    public ResponseEntity<String> likePost(@PathVariable ("userId") Long userId, @PathVariable("postId") Long postId){
        return postServices.likePost(userId, postId);
    }

    @DeleteMapping(path = "unlike/{userId}/{postId}")
    public ResponseEntity<String> unlikePost(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId){
        return postServices.unlikePost(userId, postId);
    }



}
