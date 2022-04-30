package com.example.week9project.repository;

import com.example.week9project.entity.Post;
import com.example.week9project.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("Test")
class PostRepoTest {

    @Autowired
    PostRepo postRepo;
    @Autowired
    UserRepo userRepo;


    @Test
    void shouldFindPostByUser_UserId() {
        User user = new User();
        user.setUsername("chisom");
        user.setEmail("chisom@gmail.com");
        user.setPassword("1234");
        userRepo.save(user);

        User user1 = new User();
        user1.setUsername("chiso");
        user1.setEmail("chisom@gmail.com");
        user1.setPassword("1234");
        userRepo.save(user1);

        Post post = new Post();
        post.setTitle("bags");
        post.setBody("this post is about bags");
        post.setUser(user);
        postRepo.save(post);

        Post post1 = new Post();
        post1.setTitle("bags");
        post1.setBody("this post is about bags");
        post1.setUser(user1);
        postRepo.save(post1);

        Post post2 = new Post();
        post2.setTitle("bags");
        post2.setBody("this post is about bags");
        post2.setUser(user);
        postRepo.save(post);


        List<Post> foundPost = postRepo.findPostByUser_UserId(user.getUserId());
        assertThat(post).isIn(foundPost);
//        assertThat(post2).isIn(foundPost);
//        assertThat(post1).isNotIn(foundPost);

    }
}