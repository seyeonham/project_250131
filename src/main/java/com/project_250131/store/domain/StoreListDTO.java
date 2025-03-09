package com.project_250131.store.domain;

import com.project_250131.bookmark.domain.Bookmark;
import com.project_250131.continent.entity.ContinentEntity;
import com.project_250131.menu.domain.Menu;
import com.project_250131.region.entity.RegionEntity;
import com.project_250131.store.entity.StoreEntity;
import lombok.Data;

import java.util.List;

@Data
public class StoreListDTO {

    // 맛집 1개
    private StoreDTO store;

    // 맛집 대표 메뉴 이미지
    private String menuImage;

    // 맛집 평균 평점
    private String reviewAverage;

    // 유저의 북마크 여부
    private boolean bookmark;

    // 리뷰 개수
    private int reviewCount;
}
