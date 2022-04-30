package com.example.week9project.repository;

import com.example.week9project.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikeRepo extends JpaRepository<CommentLike, Long> {
    List<CommentLike> findCommentLikesById(Long commentId);
    CommentLike findCommentLikesByIdAndPostIdAndAndUserId(Long commentId, Long postId, Long userId);
}
