package com.project_250131.menu.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Menu {

    private int id;
    private int storeId;
    private String imagePath;
    private String name;
    private int price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
