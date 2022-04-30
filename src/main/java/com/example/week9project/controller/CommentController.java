package com.example.week9project.controller;

import com.example.week9project.dto.CommentDto;
import com.example.week9project.entity.Comment;
import com.example.week9project.repository.CommentRepo;
import com.example.week9project.service.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentServices commentServices;

    @Autowired
    public CommentController(CommentServices commentServices) {
        this.commentServices = commentServices;
    }

    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("postId") Long postId,@PathVariable("userId") Long userId){
        return commentServices.createNewComment(userId, postId, commentDto);
    }

    @DeleteMapping(path = "/{id}/{userId}")
    public ResponseEntity<String > deleteComment(@PathVariable("id") long id, @PathVariable("userId") long userId){
        commentServices.deleteComment(id, userId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }


    @PutMapping(path = "/{id}/{userId}")
    public ResponseEntity<CommentDto> editComment(@PathVariable("id") long id, @PathVariable("userId") long userId, @RequestBody CommentDto commentDto ){
        return commentServices.editComment(id, userId, commentDto);
    }

    @PostMapping(value = "likes/{userId}/{postId}/{id}")
    public ResponseEntity<String> likeComment(@PathVariable ("userId") Long userId, @PathVariable("postId") Long postId, @PathVariable("id") Long id){
        return commentServices.likeComment(userId, postId, id);
    }

    @DeleteMapping(path = "unlike/{userId}/{postId}/{id}")
    public ResponseEntity<String> unlikeComment(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId, @PathVariable("id") Long id){
        return commentServices.unlikeComment(id, postId, userId);
    }

}
