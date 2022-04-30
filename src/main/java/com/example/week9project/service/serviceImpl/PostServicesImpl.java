package com.example.week9project.service.serviceImpl;

import com.example.week9project.dto.PostDto;
import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.Category;
import com.example.week9project.entity.Post;
import com.example.week9project.entity.PostLike;
import com.example.week9project.entity.User;
import com.example.week9project.exception.ApiRequestException;
import com.example.week9project.repository.CategoryRepo;
import com.example.week9project.repository.PostLikeRepo;
import com.example.week9project.repository.PostRepo;
import com.example.week9project.repository.UserRepo;
import com.example.week9project.service.PostServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServicesImpl implements PostServices {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final PostLikeRepo postLikeRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper mapper;

    @Autowired
    public PostServicesImpl(PostRepo postRepo, UserRepo userRepo, PostLikeRepo postLikeRepo, CategoryRepo categoryRepo, ModelMapper mapper) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.postLikeRepo = postLikeRepo;
        this.categoryRepo = categoryRepo;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> newList = postRepo.findAll().stream()
                .map(post -> mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        /*List<PostDto> newList = new ArrayList<>();
        List<Post> posts = postRepo.findAll();
        for(Post post: posts){
            newList.add(mapper.map(post, PostDto.class));
        }*/
        return new ResponseEntity<>(newList, HttpStatus.FOUND);
    }




    @Override
    public ResponseEntity<PostDto> createNewPost(Long userId, Long categoryId, PostDto postDto) {
        User user = userRepo.findById(userId).get();
        Category category = categoryRepo.findById(categoryId).get();
        Post post = mapper.map(postDto, Post.class);
        post.setDatePosted(LocalDateTime.now());
        post.setUser(user);
        post.setCategory(category);
        postRepo.save(post);

        categoryRepo.save(category);
        return new ResponseEntity<>(mapper.map(post, PostDto.class), HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<PostDto> editPost(Long postId, Long userId, PostDto postDto) {
        User user = userRepo.getById(userId);
        Post post = postRepo.getById(postId);
        if (post.getUser().getUserId().equals(userId)) {
            post.setTitle(postDto.getTitle());
            post.setBody(postDto.getBody());
            post.setDatePosted(LocalDateTime.now());
            post.setUser(user);
            postRepo.save(post);
        }
        return new ResponseEntity<>(mapper.map(post, PostDto.class), HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<String> deletePost(Long postId){
      boolean exists = postRepo.existsById(postId);
      if(!exists) {
          throw new ApiRequestException("Post does not exist");
      }
        postRepo.deleteById(postId);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @Override
    public  ResponseEntity<PostDto> findPostById(long postId){
        Post post = postRepo.findById(postId).get();
        PostDto map = mapper.map(post, PostDto.class);
        post.setNoOfLikes(postLikeRepo.findPostLikesByPostId(postId).size());
        return new ResponseEntity<>(map, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<String> likePost(Long userId, Long postId){
        Optional<User> user1 = userRepo.findById(userId);
        if(user1.isEmpty()){
            throw new ApiRequestException("no such user exists");
        }
        Optional<Post> post1 = postRepo.findById(postId);
        if(post1.isEmpty()){
            throw new ApiRequestException("no such post exist");
        }
        PostLike postLike1 = postLikeRepo.findPostLikeByPostIdAndUserId(postId, userId);
        if(postLike1 != null){
            throw new ApiRequestException("Cannot like post more than once");
        }
        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepo.save(postLike);
        Post post = postRepo.findById(postId).get();
        long count = post.getNoOfLikes() + 1;
        post.setNoOfLikes(count);
        postRepo.save(post);
        return new ResponseEntity<>("post with id "+ postId + " liked successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> unlikePost(Long userId, Long postId){
        PostLike postLike2 = postLikeRepo.findPostLikeByPostIdAndUserId(postId, userId);
        if(postLike2 != null){
            postLikeRepo.delete(postLike2);
            Post post = postRepo.findById(postId).get();
            post.setNoOfLikes(post.getNoOfLikes()-1);
            postRepo.save(post);
        } else {
            throw new ApiRequestException("Kindly like post");
        }
        return new ResponseEntity<>("Unlike successful", HttpStatus.OK);
    }


}
