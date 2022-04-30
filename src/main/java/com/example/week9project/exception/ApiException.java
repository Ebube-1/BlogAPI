package com.example.week9project.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter

@AllArgsConstructor
@RequiredArgsConstructor
public class ApiException {
    private Date timeStamp;
    private String message;
    private String details;


}
