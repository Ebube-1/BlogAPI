package com.example.week9project.service.serviceImpl;

import com.example.week9project.Role;
import com.example.week9project.dto.CategoryDto;
import com.example.week9project.entity.Category;
import com.example.week9project.entity.User;
import com.example.week9project.exception.ApiRequestException;
import com.example.week9project.repository.CategoryRepo;
import com.example.week9project.repository.PostRepo;
import com.example.week9project.repository.UserRepo;
import com.example.week9project.service.CategoryServices;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryServicesImpl implements CategoryServices {

    private final CategoryRepo categoryRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ModelMapper mapper;

    public CategoryServicesImpl(CategoryRepo categoryRepo, PostRepo postRepo, UserRepo userRepo, ModelMapper mapper) {
        this.categoryRepo = categoryRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.mapper = mapper;
    }


    @Override
    public ResponseEntity<CategoryDto> createCategory(Long userId, CategoryDto categoryDto) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ApiRequestException("User not found"));
        if(user.getRole().equals(Role.ADMIN)) {
            Category category = new Category();
            category.setCategoryName(categoryDto.getCategoryName());
            category.setUser(user);
            userRepo.save(user);
            categoryRepo.save(category);
            return new ResponseEntity<>(mapper.map(category, CategoryDto.class), HttpStatus.OK);
        } else{
            throw new ApiRequestException("Not Authorized");
        }
    }

    @Override
    public ResponseEntity<String> editCategory(Long categoryId, Long userId, CategoryDto categoryDto){
        Category category = categoryRepo.getById(categoryId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ApiRequestException("User not found"));
        if(user.getRole().equals(Role.ADMIN)){
            category.setCategoryName(categoryDto.getCategoryName());
            categoryRepo.save(category);
        } else {
            throw new ApiRequestException("Cannot edit category name");
        }
        return new ResponseEntity<>("Edit successful", HttpStatus.OK );
    }

    @Override
    public ResponseEntity<String> deleteCategory(long categoryId, long userId){
        User user = userRepo.getById(userId);
        boolean exists = categoryRepo.existsById(categoryId);
        if(!exists){
            throw new ApiRequestException("There is no category for this post");
        }
        if(user.getRole().equals(Role.USER)){
            throw new ApiRequestException("Not authorized to delete a category");
        }
        categoryRepo.deleteById(categoryId);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<String> deleteComment(long postId) {
//        boolean exist = commentRepo.existsById(postId);
//        if(!exist){
//            throw new ApiRequestException("There is no comment for this post");
//        }
//        commentRepo.deleteById(postId);
//        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
//    }


}
