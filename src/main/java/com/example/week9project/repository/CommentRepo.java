package com.example.week9project.repository;

import com.example.week9project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository <Comment, Long> {
    List<Comment> findCommentsByPost_PostId(long postId);
}
