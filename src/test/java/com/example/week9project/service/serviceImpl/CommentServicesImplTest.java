package com.example.week9project.service.serviceImpl;

import com.example.week9project.Role;
import com.example.week9project.dto.CommentDto;
import com.example.week9project.dto.PostDto;
import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.Category;
import com.example.week9project.entity.Comment;
import com.example.week9project.entity.Post;
import com.example.week9project.entity.User;
import com.example.week9project.repository.CommentRepo;
import com.example.week9project.repository.PostRepo;
import com.example.week9project.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServicesImplTest {

    @Mock
    private CommentRepo commentRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PostRepo postRepo;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CommentServicesImpl underTest;

    private Comment comment;
    private User user;
    private Post post;

    @BeforeEach
    void setUp() {

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUsername("ebube");
        registrationDto.setEmail("ebube@gmail.com");
        registrationDto.setPassword("1234");

        user = new User();
        user.setUserId(1L);
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword());

        PostDto postDto = new PostDto();
        postDto.setTitle("new post");
        postDto.setBody("this is a new post");


        Post post = new Post();
        post.setPostId(1L);
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setUser(user);
        post.setDatePosted(LocalDateTime.now());
        post.setCategory(new Category());
        post.setNoOfComments(0);
        post.setNoOfLikes(0);

        CommentDto commentDto = new CommentDto();
        commentDto.setText("first comment");

        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setTimePosted(LocalDateTime.now());
        comment.setPost(post);
        comment.setUser(user);
        comment.setId(1L);

    }

    @Test
    void deleteComment() {
        comment = new Comment();
        comment.setText("comment one");
        comment.setId(1L);
        comment.setNoOfLikes(0);
        comment.setUser(user);
        comment.setPost(post);
        when(commentRepo.findCommentByUserUserIdAndId(comment.getId(), comment.getUser().getUserId())).thenReturn(comment);
        ResponseEntity<String> response = underTest.deleteComment(comment.getId(), comment.getUser().getUserId());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo("Deleted successfully");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createNewComment() {

    }

    @Test
    void editComment() {
    }

    @Test
    void likeComment() {
    }

    @Test
    void unlikeComment() {
    }
}