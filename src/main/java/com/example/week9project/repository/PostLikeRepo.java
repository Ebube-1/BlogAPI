package com.example.week9project.repository;

import com.example.week9project.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepo extends JpaRepository<PostLike, Long> {
    List<PostLike> findPostLikesByPostId(Long postId);

    PostLike findPostLikeByPostIdAndUserId(Long postId, Long userId);
}
