package com.example.week9project.service.serviceImpl;

import com.example.week9project.dto.PostDto;
import com.example.week9project.entity.Comment;
import com.example.week9project.entity.Post;
import com.example.week9project.entity.User;
import com.example.week9project.repository.CommentRepo;
import com.example.week9project.repository.PostRepo;
import com.example.week9project.repository.UserRepo;
import com.example.week9project.service.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServicesImpl implements CommentServices {

    private final UserRepo userRepo;
    private final CommentRepo commentRepo;
    private final PostRepo postRepo;

    @Autowired
    public CommentServicesImpl(UserRepo userRepo, CommentRepo commentRepo, PostRepo postRepo) {
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }


    @Override
    public boolean deleteComment(Long id) {
        return false;
    }


    @Override
    public List<Comment> getAllCommentsInAPost(long postId) {
        return commentRepo.findCommentsByPost_PostId(postId);
    }

    @Override
    public Comment createNewComment(long userId, long postId, PostDto postDto) {
        Comment comment = new Comment();
        User user = userRepo.getById(userId);
        Post post = postRepo.getById(postId);
        comment.setText(postDto.getBody());
        comment.setPost(post);
        comment.setUser(user);
        commentRepo.save(comment);
        return comment;
    }
}
