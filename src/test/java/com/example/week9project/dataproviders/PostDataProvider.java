package com.example.week9project.dataproviders;

import com.example.week9project.entity.Category;
import com.example.week9project.entity.Post;
import com.example.week9project.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostDataProvider {

    private final PostRepo postRepo;
    private final UserDataProvider userDataProvider;

    public PostRepo postRepo() {
        return postRepo;
    }

    public Post save() {
        Post post = new Post();
        post.setPostId(1L);
        post.setTitle("post title");
        post.setBody("post body");
        post.setUser(userDataProvider.save());
        post.setDatePosted(LocalDateTime.now());
        post.setCategory(new Category());
        post.setNoOfComments(0);
        post.setNoOfLikes(0);
        return postRepo.save(post);
    }
}
