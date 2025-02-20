package com.project_250131.bookmark.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Bookmark {

    private int id;
    private int userId;
    private int storeId;
    private boolean deleteYn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
