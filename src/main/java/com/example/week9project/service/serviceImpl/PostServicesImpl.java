package com.example.week9project.service.serviceImpl;

import com.example.week9project.dto.PostDto;
import com.example.week9project.entity.Post;
import com.example.week9project.entity.User;
import com.example.week9project.exception.ApiRequestException;
import com.example.week9project.repository.PostRepo;
import com.example.week9project.repository.UserRepo;
import com.example.week9project.service.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PostServicesImpl implements PostServices {

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    @Autowired
    public PostServicesImpl(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

//    @Override
//    public boolean deletePost(Long postId) {
//        Post thePost = postRepo.findOne(postId);
//        if(thePost == null)
//            return false;
//        postRepo.delete(postId);
//        return true;
//
//    }

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public void insert(Post post) {
        postRepo.save(post);
    }

//    orElseThrow(() -> new ApiRequestException("User not found"));

    @Override
    public Post createNewPost(Long userId, PostDto postDto) {
        User user = userRepo.findById(userId).get();
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setDatePosted(Date.valueOf(LocalDate.now()));
        post.setTimePosted(Time.valueOf(LocalTime.now()));
        post.setUser(user);
        postRepo.save(post);
        return post;
    }

    @Override
    public List<Post> findPostsByUserId(Long userId) {
        return postRepo.findPostByUser_UserId(userId);
    }

    @Override
    public void deletePost(Long postId){
      boolean exists = postRepo.existsById(postId);
      if(!exists) {
          throw new ApiRequestException("Post does not exist");
      }
        postRepo.deleteById(postId);
    }


}
