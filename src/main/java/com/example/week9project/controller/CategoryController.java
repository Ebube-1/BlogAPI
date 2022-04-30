package com.example.week9project.controller;

import com.example.week9project.dto.CategoryDto;
import com.example.week9project.dto.PostDto;
import com.example.week9project.entity.Category;
import com.example.week9project.entity.Post;
import com.example.week9project.repository.UserRepo;
import com.example.week9project.service.CategoryServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/categories")
public class CategoryController {

    private final CategoryServices categoryServices;
    private final UserRepo userRepo;

    public CategoryController(CategoryServices categoryServices, UserRepo userRepo) {
        this.categoryServices = categoryServices;
        this.userRepo = userRepo;
    }

    @PostMapping(value = "{userId}")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto, @PathVariable("userId") Long userId){
        return categoryServices.createCategory(userId, categoryDto);
    }

    @PutMapping(path = "edit/{categoryId}/{userId}")
    public ResponseEntity<String> editCategory(@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Long categoryId, @PathVariable("userId") Long userId){
        return categoryServices.editCategory(categoryId, userId, categoryDto);
    }

    @DeleteMapping(value = "delete/{categoryId}/{userId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId, @PathVariable("userId") Long userId){
        return categoryServices.deleteCategory(categoryId, userId);
    }


}
