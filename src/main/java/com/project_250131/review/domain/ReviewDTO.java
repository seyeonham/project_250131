package com.project_250131.review.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private int id;
    private int storeId;
    private int userId;
    private String userName;
    private String imagePath;
    private int point;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
