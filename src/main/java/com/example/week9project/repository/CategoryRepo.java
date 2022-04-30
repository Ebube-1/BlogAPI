package com.example.week9project.repository;

import com.example.week9project.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findAllByCategoryId(Long categoryId);
    Category findByCategoryName(String categoryName);
}
