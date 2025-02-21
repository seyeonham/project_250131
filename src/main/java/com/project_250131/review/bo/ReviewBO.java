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

    public double getReviewPointByStoreId(int storeId) {
        Double point = reviewMapper.selectReviewPointByStoreId(storeId);
        if (point == null) {
            return 0;
        }
        return point;
    }

    public List<Review> getReviewListByStoreId(int storeId) {
        return reviewMapper.selectReviewListByStoreId(storeId);
    }

}
