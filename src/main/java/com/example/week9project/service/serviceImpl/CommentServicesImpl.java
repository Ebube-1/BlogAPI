package com.example.week9project.service.serviceImpl;

import com.example.week9project.dto.CommentDto;
import com.example.week9project.entity.*;
import com.example.week9project.exception.ApiRequestException;
import com.example.week9project.repository.CommentLikeRepo;
import com.example.week9project.repository.CommentRepo;
import com.example.week9project.repository.PostRepo;
import com.example.week9project.repository.UserRepo;
import com.example.week9project.service.CommentServices;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServicesImpl implements CommentServices {

    private final UserRepo userRepo;
    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final CommentLikeRepo commentLikeRepo;
    private final ModelMapper mapper;

    @Autowired
    public CommentServicesImpl(UserRepo userRepo, CommentRepo commentRepo, PostRepo postRepo, CommentLikeRepo commentLikeRepo, ModelMapper mapper) {
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.commentLikeRepo = commentLikeRepo;
        this.mapper = mapper;
    }


    @Override
    public ResponseEntity<String> deleteComment(long id, long userId) {
        Comment comment = commentRepo.findCommentByUserUserIdAndId(userId, id);
        if(comment != null){
            commentRepo.deleteById(id);
        } else {
            throw new ApiRequestException("cannot delete this comment");
        }
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<CommentDto> createNewComment(long userId, long postId, CommentDto commentDto) {
        Comment comment = new Comment();
        User user = userRepo.getById(userId);
        Post post = postRepo.getById(postId);
            comment.setText(commentDto.getText());
            comment.setTimePosted(LocalDateTime.now());
            comment.setUser(user);
            comment.setPost(post);
            commentRepo.save(comment);
            return new ResponseEntity<>(mapper.map(comment, CommentDto.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentDto> editComment(long id, long userId, CommentDto commentDto) {
        Comment comment = commentRepo.getById(id);
        if (comment.getUser().getUserId().equals(userId)) {
            mapper.map(commentDto, Comment.class);
            comment.setText(commentDto.getText());
            commentRepo.save(comment);

        } else {
            throw new ApiRequestException("Sorry you cannot edit this comment");
        }
        return new ResponseEntity<>(mapper.map(comment, CommentDto.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> likeComment(Long userId, Long postId, Long id){
        Optional<User> user2 = userRepo.findById(userId);
        if(user2.isEmpty()){
            throw new ApiRequestException("no such user exists");
        }
        Optional<Post> post1 = postRepo.findById(postId);
        if(post1.isEmpty()) {
            throw new ApiRequestException("no such post exist");
        }
        CommentLike commentLike = commentLikeRepo.findCommentLikesByIdAndPostIdAndAndUserId(id, postId, userId);
        if(commentLike != null){
            throw new ApiRequestException("Cannot like a comment more than once");
        }
       // CommentLike commentLike1 = mapper.map(commentLikeRepo, CommentLike.class);
        CommentLike commentLike1 = new CommentLike();
        commentLike1.setId(id);
        commentLike1.setPostId(postId);
        commentLike1.setUserId(userId);
        commentLikeRepo.save(commentLike1);

        Comment comment = commentRepo.findById(id).get();
        comment.setNoOfLikes(comment.getNoOfLikes() + 1);
        commentRepo.save(comment);

        return new ResponseEntity<>("comment with id " + id + " liked successfully", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<String> unlikeComment(Long id, Long postId, Long userId){
        CommentLike commentLike2 = commentLikeRepo.findCommentLikesByIdAndPostIdAndAndUserId(id, postId, userId);
        if(commentLike2 != null){
            commentLikeRepo.delete(commentLike2);
            Comment comment = commentRepo.findById(id).get();
            comment.setNoOfLikes(comment.getNoOfLikes() - 1);
            commentRepo.save(comment);
        } else {
            throw new ApiRequestException("Kindly like comment");
        }
        return new ResponseEntity<>("Unlike successful", HttpStatus.OK);
    }

}
