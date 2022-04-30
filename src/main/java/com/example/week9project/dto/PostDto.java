package com.example.week9project.dto;

import com.example.week9project.entity.Category;
import lombok.*;



@Getter
@Setter
@RequiredArgsConstructor

public class PostDto {
    private String title;
    private String body;

}
