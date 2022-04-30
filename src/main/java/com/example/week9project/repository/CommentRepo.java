package com.example.week9project.repository;

import com.example.week9project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository <Comment, Long> {
            Comment findCommentByUserUserIdAndId (long userId, long id);
}
