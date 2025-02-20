package com.project_250131.review.bo;

import com.project_250131.review.domain.Review;
import com.project_250131.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewBO {

    private final ReviewMapper reviewMapper;

    public List<Review> getReviewListByStoreId(int storeId) {
        return reviewMapper.selectReviewListByStoreId(storeId);
    }
}
