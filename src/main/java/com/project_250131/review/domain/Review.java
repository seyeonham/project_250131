package com.project_250131.review.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {

    private int id;
    private int storeId;
    private int userId;
    private String imagePath;
    private Double point;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
