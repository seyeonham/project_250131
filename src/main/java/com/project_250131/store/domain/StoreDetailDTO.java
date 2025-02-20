package com.project_250131.store.domain;

import com.project_250131.menu.domain.Menu;
import com.project_250131.review.domain.Review;
import com.project_250131.store.entity.StoreEntity;
import lombok.Data;

import java.util.List;

@Data
public class StoreDetailDTO {

    private StoreEntity store;

    private List<Menu> menuList;

    private List<Review> reviewList;

    private int bookmarkCount;
}
