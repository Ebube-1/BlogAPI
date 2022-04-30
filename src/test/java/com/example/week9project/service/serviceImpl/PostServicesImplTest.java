package com.example.week9project.service.serviceImpl;

import com.example.week9project.dto.PostDto;
import com.example.week9project.dto.RegistrationDto;
import com.example.week9project.entity.Category;
import com.example.week9project.entity.Post;
import com.example.week9project.entity.User;
import com.example.week9project.exception.ApiRequestException;
import com.example.week9project.repository.CategoryRepo;
import com.example.week9project.repository.PostLikeRepo;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@DataJpaTest
//@ContextConfiguration(classes = PostDataProvider.class)
class PostServicesImplTest {

    @Mock
    private PostRepo postRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private PostLikeRepo postLikeRepo;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    PostServicesImpl underTest;

    private PostDto postDto;
    private Post post;
    private User user;
    private Category category;

    @BeforeEach
    void setUp() {
        postDto = new PostDto();
        postDto.setTitle("new post");
        postDto.setBody("this is a new post");

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUsername("ebube");
        registrationDto.setEmail("ebube@gmail.com");
        registrationDto.setPassword("1234");

        user = new User();
        user.setUserId(1L);
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword());

        post = new Post();
        post.setPostId(1L);
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setUser(user);
        post.setDatePosted(LocalDateTime.now());
        post.setCategory(new Category());
        post.setNoOfComments(0);
        post.setNoOfLikes(0);


        category = new Category();
        category.setCategoryName("bags");
        category.setUser(user);

    }

    @Test
    void shouldGetAllPosts() {
        List<Post> postList = List.of(post);
        when(postRepo.findAll()).thenReturn(postList);
        when(mapper.map(post, PostDto.class)).thenReturn(postDto);
        ResponseEntity<List<PostDto>> response = underTest.getAllPosts();

        assertThat(Objects.requireNonNull(response.getBody()).size()).isEqualTo(postList.size());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

   /* @Autowired
    private PostDataProvider postDataProvider;

    @Test
    void whenGetAllPost_ShouldReturnAllPosts() {
        long initialNoOfPosts = postDataProvider.postRepo().count();
        int noOfPost= new Random().nextInt(10) + 1; //number between 1 and 10

        List<Post> savedPosts = new ArrayList<>();
        for(int i = 0; i < noOfPost; i++) {
            savedPosts.add(postDataProvider.save());
        }

        ResponseEntity<List<PostDto>> postsResponse = underTest.getAllPosts();
        assertThat(HttpStatus.FOUND).isEqualTo(postsResponse.getStatusCode());

        List<PostDto> allPosts = postsResponse.getBody();
        assertThat(allPosts).isNotNull();
        assertThat(initialNoOfPosts + noOfPost).isEqualTo(allPosts.size());

        for(Post savedPost : savedPosts) {
            boolean postExists = allPosts.stream()
                    .anyMatch(returnedPost -> returnedPost.getTitle().equals(savedPost.getTitle()) &&
                            returnedPost.getBody().equals(savedPost.getBody()));
            assertThat(postExists).isTrue();
        }
    }*/

    @Test
    void insert() {
    }

    @Test
    void shouldBeAbleToCreateNewPost() {
        when(userRepo.findById(user.getUserId())).thenReturn(Optional.ofNullable(user));
        when(categoryRepo.findById(category.getCategoryId())).thenReturn(Optional.ofNullable(category));
        when(mapper.map(postDto, Post.class)).thenReturn(post);
        when(mapper.map(post, PostDto.class)).thenReturn(postDto);
        ResponseEntity<PostDto> response = underTest.createNewPost(user.getUserId(), category.getCategoryId(), postDto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo(post.getTitle());
        assertThat(response.getBody().getBody()).isEqualTo(post.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @Test
    void shouldBeAbleToEditPost() {
        when(userRepo.getById(user.getUserId())).thenReturn(user);
        when(postRepo.getById(post.getPostId())).thenReturn(post);
        when(mapper.map(post, PostDto.class)).thenReturn(postDto);
        ResponseEntity<PostDto> response = underTest.editPost(post.getPostId(), user.getUserId(), postDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getBody()).isEqualTo(post.getBody());

    }

    @Test
    void shouldThrowExceptionWhenDeletingPostThatDoesNotExist(){
        when(postRepo.existsById(post.getPostId())).thenReturn(false);

        assertThatThrownBy(()-> underTest.deletePost(post.getPostId()))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("Post does not exist");
    }


    @Test
    void deletePost() {
        post = new Post();
        post.setPostId(1L);
        post.setTitle("shoes");
        post.setBody("about shoes");


        when(postRepo.existsById(post.getPostId())).thenReturn(true);
        ResponseEntity<String> response = underTest.deletePost(post.getPostId());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo("Deleted successfully");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldFindPostAById() {
        when(postRepo.findById(post.getPostId())).thenReturn(Optional.ofNullable(post));
        when(mapper.map(post, PostDto.class)).thenReturn(postDto);
       // when(postLikeRepo.findPostLikesByPostId(post.getPostId()).size()).thenReturn(0);
        ResponseEntity<PostDto> response = underTest.findPostById(post.getPostId());

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getBody()).isEqualTo(post.getBody());
        assertThat(response.getBody().getTitle()).isEqualTo(post.getTitle());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void likePost() {
    }

    @Test
    void unlikePost() {
    }
}