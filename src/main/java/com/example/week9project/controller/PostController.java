package com.example.week9project.controller;

import com.example.week9project.dto.LoginDto;
import com.example.week9project.dto.PostDto;
import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.Post;
import com.example.week9project.repository.PostRepo;
import com.example.week9project.service.PostServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/post")
public class PostController {
    private final PostRepo postRepo;
    private final PostServices postServices;

    public PostController(PostRepo postRepo, PostServices postServices) {
        this.postRepo = postRepo;
        this.postServices = postServices;
    }

    @DeleteMapping(path = "{id}")
    public void deletePost (@PathVariable("id") Long postId){
        postServices.deletePost(postId);
    }

    @PostMapping(value = "/create/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto,@PathVariable Long userId){
        return new ResponseEntity<>(postServices.createNewPost(userId, postDto), HttpStatus.CREATED);
    }


//    @PostMapping(value = "/login")
//    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
//        return new ResponseEntity<String>(userServices.login(loginDto), HttpStatus.ACCEPTED);
//    }


}
