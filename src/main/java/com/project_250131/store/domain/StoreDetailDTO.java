package com.project_250131.store.domain;

import com.project_250131.menu.domain.Menu;
import com.project_250131.review.domain.Review;
import com.project_250131.store.entity.StoreEntity;
import lombok.Data;

import java.util.List;

@Data
public class StoreDetailDTO {

    // 맛집 1개
    private StoreEntity store;

    // 맛집 메뉴 리스트
    private List<Menu> menuList;

    // 맛집 리뷰 리스트
    private List<Review> reviewList;

    // 맛집 리뷰 평점
    private double reviewAverage;

    // 북마크 횟수
    private int bookmarkCount;
}
