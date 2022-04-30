package com.example.week9project.service;

import com.example.week9project.dto.CategoryDto;
import com.example.week9project.entity.Category;
import org.springframework.http.ResponseEntity;

public interface CategoryServices {
    ResponseEntity<CategoryDto> createCategory(Long userId, CategoryDto categoryDto);

    ResponseEntity<String> editCategory(Long categoryId, Long userId, CategoryDto categoryDto);

    ResponseEntity<String> deleteCategory(long categoryId, long userId);
}
