package com.example.week9project.repository;

import com.example.week9project.entity.Post;
import com.example.week9project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository< Post, Long> {

//    void deleteById(Long postId);
    List<Post> findPostByUser_UserId(long userId);

}
